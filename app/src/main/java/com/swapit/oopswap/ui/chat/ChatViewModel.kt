package com.swapit.oopswap.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
import com.swapit.oopswap.ui.base.BaseViewModelFactory
import com.swapit.oopswap.ui.navigation.NavItem
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class ChatViewModel(
    private val repository: ChatRepository,
    private val stompModule: StompModule,
) : ViewModel() {
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
        stompModule.sendMessage(message, chatRoomId)
    }

    fun enterChatRoom(newChatRoomId: Long) {
        viewModelScope.launch {
            // 기존 채팅방 구독 해제 (필요할 때만)
            if (chatRoomId.longValue != 0L) {
                stompModule.unsubscribeFromChatRoom(chatRoomId.longValue)
            }

            // 새로운 채팅방 ID 설정
            chatRoomId.longValue = newChatRoomId

            // 새로운 채팅방 구독
            stompModule.subscribeToChatRoom(newChatRoomId, chatList)
        }
    }

    private suspend fun createChatRoomSync(goodsId: GoodsIdRequest): Long = repository.createChatRoom(goodsId).getOrNull()?.results
        ?: 0L

    private suspend fun createChatRoomTradeSync(tradesId: TradesIdRequest): Long = repository.createSwapChatRoom(tradesId).getOrNull()?.results
        ?: 0L

    fun initiateChatFlow(
        goodsId: Long,
        navController: NavController,
    ) {
        viewModelScope.launch {
            val goodsIdRequest = GoodsIdRequest(goodsId)
            val chatRoomId = createChatRoomSync(goodsIdRequest)
            this@ChatViewModel.chatRoomId.longValue = chatRoomId

            if (chatRoomId != 0L) {
                // 채팅방 정보(Result<ChatRoomInfo>) 언랩핑
                repository.chatRoomInfo(chatRoomId)
                    .onSuccess { info ->
                        chatRoomProduct.value = info
                    }
                    .onFailure { t ->
                        Log.e(TAG, "ChatRoomInfo 조회 실패", t)
                    }
                chatList = stompModule.subscribeToChatRoom(chatRoomId, mutableStateListOf())

                /* chatRoomProduct.value = repository.chatRoomInfo(chatRoomId)
                 val initialChatList = mutableStateListOf<Chat>()
                 chatList = stompModule.subscribeToChatRoom(chatRoomId, initialChatList)*/
                navController.navigate(NavItem.ChatRoom.screenRoute + "/$chatRoomId")
            } else {
                Log.e("ChatViewModel", "Chat room ID is not set. Failed to navigate.")
            }
        }
    }

    fun initiateChatSwapFlow(
        tradesId: Long,
        navController: NavController,
    ) {
        viewModelScope.launch {
            val tradesIdRequest = TradesIdRequest(tradesId)
            val chatRoomId = createChatRoomTradeSync(tradesIdRequest)
            this@ChatViewModel.chatRoomId.longValue = chatRoomId
            if (chatRoomId != 0L) {
                repository.chatRoomInfo(chatRoomId)
                    .onSuccess { info ->
                        chatRoomProduct.value = info
                    }
                    .onFailure { t ->
                        Log.e(TAG, "ChatRoomInfo 조회 실패", t)
                    }
                chatList = stompModule.subscribeToChatRoom(chatRoomId, mutableStateListOf())

                /*chatRoomProduct.value = repository.chatRoomInfo(chatRoomId)
                val initialChatList = mutableStateListOf<Chat>()
                chatList = stompModule.subscribeToChatRoom(chatRoomId, initialChatList) as SnapshotStateList<Chat>*/
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
        viewModelScope.launch {
            repository.chatRoomInfo(chatroomId)
                .onSuccess { info ->
                    chatRoomProduct.value = info
                    onComplete(info)
                }
                .onFailure { t ->
                    Log.e(TAG, "ChatRoomProduct 가져오기 실패", t)
                    onComplete(null)
                }
            /*try {
                val product = repository.chatRoomInfo(chatroomId)
                chatRoomProduct.value = product
                onComplete(product) // 성공 시 콜백 호출
            } catch (e: Exception) {
                Log.e("ChatViewModel", "ChatRoomProduct 가져오기 실패: ${e.message}")
                onComplete(null) // 실패 시 null 반환
            }*/
        }
    }

    fun fetchChatList(chatroomId: Long) {
        viewModelScope.launch {
            val newChatList = repository.chatList(chatroomId)
                .getOrNull()?.chatList
                ?.map { dto -> dto.toDomain() }
                ?: emptyList()
//            val newChatList = repository.chatList(chatroomId).chatList.map { it.toDomain() }
            chatList.clear()
            chatList.addAll(newChatList)
        }
    }

    fun fetchChatRoomList() {
        viewModelScope.launch {
            val newChatRoomList = repository.chatRoomList()
                .getOrNull()
                ?: emptyList()
//            val newChatRoomList = repository.chatRoomList() // 여기서 ArrayList 반환
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
