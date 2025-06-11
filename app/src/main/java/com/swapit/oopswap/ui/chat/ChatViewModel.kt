package com.swapit.oopswap.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.swapit.oopswap.data.datasource.remote.StompModule
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.ChatRequest
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.swapit.oopswap.data.mapper.toDomain
import com.swapit.oopswap.domain.model.chat.Chat
import com.swapit.oopswap.domain.model.chat.ChatRoom
import com.swapit.oopswap.domain.model.chat.ChatRoomInfo
import com.swapit.oopswap.domain.repository.ChatRepository
import com.swapit.oopswap.ui.base.BaseViewModel
import com.swapit.oopswap.ui.base.BaseViewModelFactory
import com.swapit.oopswap.ui.navigation.NavItem
import java.util.concurrent.atomic.AtomicInteger

class ChatViewModel(
    private val repository: ChatRepository,
    private val stompModule: StompModule,
) : BaseViewModel() {
    private val subscriptionCounter = AtomicInteger(0)
    private val subscriptionIds = mutableMapOf<Long, String>() // chatRoomId 별 구독 ID 저장
    var chatRoomList = mutableStateListOf<ChatRoom>()
    var chatList = mutableStateListOf<Chat>()
    val chatRoomId = mutableLongStateOf(0L)
    val goodsId = mutableLongStateOf(0L)
    val tradesId = mutableLongStateOf(0L)
    val chatRoomProduct =
        mutableStateOf(
            ChatRoomInfo(
                goodsId = 1,
                imageUrl = "",
                title = "",
                price = 0,
                nickname = "",
                category = "",
                usersId = 1,
            ),
        )

    fun sendReadReceipt(chatRoomId: Long) {
        stompModule.sendReadReceipt(chatRoomId, chatList)
    }

    fun sendMessage(
        message: ChatRequest,
        chatRoomId: Long,
    ) {
        // 메시지 전송만 수행하고 채팅 목록 조회는 하지 않음
        stompModule.sendMessage(message, chatRoomId)
    }

    private suspend fun createChatRoomSync(goodsId: GoodsIdRequest): Long = repository.createChatRoom(goodsId).results

    private suspend fun createChatRoomTradeSync(tradesId: TradesIdRequest): Long = repository.createSwapChatRoom(tradesId).results

    fun initiateChatFlow(
        goodsId: Long,
        navController: NavController,
    ) {
        safeLaunch {
            val goodsIdRequest = GoodsIdRequest(goodsId)
            val chatRoomId = createChatRoomSync(goodsIdRequest)
            this@ChatViewModel.chatRoomId.longValue = chatRoomId

            if (chatRoomId != 0L) {
                chatRoomProduct.value = repository.chatRoomInfo(chatRoomId)

                stompModule.unsubscribeFromChatRoom(chatRoomId) // 기존 구독 해제
                chatList = mutableStateListOf() // 새로운 채팅방 구독
                stompModule.subscribeToChatRoom(chatRoomId, chatList)
                navController.navigate(NavItem.ChatRoom.screenRoute + "/$chatRoomId")
            } else {
                Log.e("ChatViewModel", "Chat room ID is not set. Failed to navigate.")
            }
        }
    }

    fun enterChatRoom(newChatRoomId: Long) {
        safeLaunch {
            if (chatRoomId.longValue != 0L && chatRoomId.longValue != newChatRoomId) {
                stompModule.unsubscribeFromChatRoom(chatRoomId.longValue)
            }

            chatRoomId.longValue = newChatRoomId
            chatList = mutableStateListOf() // 채팅 리스트 초기화
            
            // 채팅방 입장 시 한 번만 이전 메시지 로드
            fetchChatList(newChatRoomId)
            
            // 웹소켓 구독 시작
            stompModule.subscribeToChatRoom(newChatRoomId, chatList)
        }
    }

    fun initiateChatSwapFlow(
        tradesId: Long,
        navController: NavController,
    ) {
        safeLaunch {
            val tradesIdRequest = TradesIdRequest(tradesId)
            val chatRoomId = createChatRoomTradeSync(tradesIdRequest)
            this@ChatViewModel.chatRoomId.longValue = chatRoomId
            if (chatRoomId != 0L) {
                chatRoomProduct.value = repository.chatRoomInfo(chatRoomId)
                val initialChatList = mutableStateListOf<Chat>()
                chatList = stompModule.subscribeToChatRoom(chatRoomId, initialChatList) as SnapshotStateList<Chat>
                navController.navigate(NavItem.ChatRoom.screenRoute + "/$chatRoomId")
            } else {
                Log.e("ChatViewModel", "Chat room ID is not set. Failed to navigate.")
            }
        }
    }

    fun fetchChatRoomProduct(
        chatroomId: Long,
        onComplete: (ChatRoomInfo?) -> Unit,
    ) {
        safeLaunch {
            try {
                val product = repository.chatRoomInfo(chatroomId)
                chatRoomProduct.value = product
                onComplete(product) // 성공 시 콜백 호출
            } catch (e: Exception) {
                Log.e("ChatViewModel", "ChatRoomProduct 가져오기 실패: ${e.message}")
                onComplete(null) // 실패 시 null 반환
            }
        }
    }

    fun fetchChatList(chatroomId: Long) {
        safeLaunch {
            try {
                val newChatList = repository.chatList(chatroomId).chatList.map { it.toDomain() }
                chatList.clear()
                chatList.addAll(newChatList)
                Log.d(TAG, "이전 채팅 메시지 로드 완료: ${newChatList.size}개")
            } catch (e: Exception) {
                Log.e(TAG, "채팅 메시지 로드 실패: ${e.message}")
            }
        }
    }

    fun fetchChatRoomList() {
        safeLaunch {
            val newChatRoomList = repository.chatRoomList() // 여기서 ArrayList 반환
            chatRoomList.clear() // ✅ 기존 리스트 비우기
            chatRoomList.addAll(newChatRoomList) // ✅ 새로운 데이터 추가
        }
    }

    companion object {
        private const val TAG = "ChatViewModel"

        fun factory(
            repository: ChatRepository,
            stompModule: StompModule,
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                ChatViewModel(
                    repository = repository,
                    stompModule = stompModule,
                )
            }
    }
}
