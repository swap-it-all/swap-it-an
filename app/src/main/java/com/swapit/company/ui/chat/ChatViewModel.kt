package com.swapit.company.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.swapit.company.BuildConfig
import com.swapit.company.data.datasource.remote.RetrofitModule.okHttpClient
import com.swapit.company.data.datasource.remote.dto.request.chat.ChatReadRequest
import com.swapit.company.data.datasource.remote.dto.request.chat.ChatRequest
import com.swapit.company.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.swapit.company.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatResponse
import com.swapit.company.data.mapper.toDomain
import com.swapit.company.domain.model.chat.Chat
import com.swapit.company.domain.model.chat.ChatRoom
import com.swapit.company.domain.model.chat.ChatRoomInfo
import com.swapit.company.domain.repository.ChatRepository
import com.swapit.company.domain.repository.LoginRepository
import com.swapit.company.ui.base.BaseViewModelFactory
import com.swapit.company.ui.navigation.NavItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.frame.FrameBody
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import org.json.JSONObject

class ChatViewModel(private val repository: ChatRepository, private val loginRepository: LoginRepository) :
    ViewModel() {
    var chatRoomList = mutableStateListOf<ChatRoom>()
    var chatList = mutableStateListOf<Chat>()
    val chatRoomId = mutableLongStateOf(0L)
    val goodsId = mutableLongStateOf(0L)
    val tradesId = mutableLongStateOf(0L)
    private val wsClient by lazy {
        OkHttpWebSocketClient(okHttpClient())
    }
    private val stompClient = StompClient(wsClient)
    private var stompSession: StompSession? = null
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

    fun connect() {
        viewModelScope.launch {
            try {
                stompSession =
                    stompClient.connect(
                        BuildConfig.SWAP_IT_BASE_URL.replace(
                            "http",
                            "ws",
                        ) + "ws",
                        customStompConnectHeaders =
                            mapOf(
                                "Authorization" to "Bearer ${loginRepository.accessToken() ?: ""}",
                                "accept-version" to "1.2",
                            ),
                        host = "localhost",
                    )
            } catch (e: Exception) {
                Log.e("STOMP", "connect() 연결 실패: ${e.message}")
            }
        }
    }

    private fun subscribeToChatRoom() {
        viewModelScope.launch {
            if (stompSession == null) {
                Log.e("STOMP", "subscribeToChatRoom() 연결 실패")
                return@launch
            }
            try {
                val messageFlow =
                    stompSession!!.subscribe(
                        StompSubscribeHeaders(
                            destination = "/topic/chat/${chatRoomId.longValue}",
                            id = "sub-0",
                        ),
                    )
                messageFlow.collect { frame ->
                    Log.d("STOMP", "수신 메시지: ${frame.bodyAsText}")
                    frame.bodyAsText?.let { jsonMessage ->
                        try {
                            val receivedChat = Json.decodeFromString<ChatResponse>(jsonMessage)
                            chatList += receivedChat.toDomain()
                        } catch (e: Exception) {
                            Log.e("STOMP", "메시지 처리 실패: ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("STOMP", "subscribeToChatRoom 실패: ${e.message}")
            }
        }
    }

    private suspend fun createChatRoomSync(goodsId: GoodsIdRequest): Long {
        return repository.createChatRoom(goodsId).results
    }

    private suspend fun createChatRoomTradeSync(tradesId: TradesIdRequest): Long {
        return repository.createSwapChatRoom(tradesId).results
    }

    fun initiateChatFlow(
        goodsId: Long,
        navController: NavController,
    ) {
        viewModelScope.launch {
            val goodsIdRequest = GoodsIdRequest(goodsId)
            val chatRoomId = createChatRoomSync(goodsIdRequest)
            this@ChatViewModel.chatRoomId.longValue = chatRoomId

            if (chatRoomId != 0L) {
                chatRoomProduct.value = repository.chatRoomInfo(chatRoomId)
                subscribeToChatRoom()
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
                chatRoomProduct.value = repository.chatRoomInfo(chatRoomId)
                subscribeToChatRoom()
                navController.navigate(NavItem.ChatRoom.screenRoute + "/$chatRoomId")
            } else {
                Log.e("ChatViewModel", "Chat room ID is not set. Failed to navigate.")
            }
        }
    }

    fun sendMessage(
        message: ChatRequest,
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                // 토큰 갱신 로직
                if (isAccessTokenExpired()) {
                    val newTokens = loginRepository.refresh(loginRepository.refreshToken()!!)
                    Log.d(TAG, "새로운 액세스 토큰 발급: ${newTokens.accessToken}")
                }

                // STOMP 메시지 전송
                stompSession?.send(
                    headers =
                        StompSendHeaders(
                            destination = "/app/chat/${chatRoomId.longValue}",
                            customHeaders =
                                mapOf(
                                    "content-type" to "application/json",
                                    "Authorization" to "Bearer ${loginRepository.accessToken() ?: ""}",
                                ),
                        ),
                    body = FrameBody.Text(Json.encodeToString(ChatRequest.serializer(), message) + "\\0"),
                )
                delay(100)
                onSuccess()
                Log.d(TAG, "메시지 전송 성공: $message")
            } catch (e: Exception) {
                Log.e(TAG, "메시지 전송 실패: ${e.message}")
            }
        }
    }

    // 토큰 만료 여부 확인 함수
    private fun isAccessTokenExpired(): Boolean {
        val token = loginRepository.accessToken() ?: return true
        val parts = token.split(".")
        if (parts.size < 3) return true

        val payload = String(android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE))
        val expiration = JSONObject(payload).optLong("exp", 0)
        return System.currentTimeMillis() / 1000 >= expiration
    }

    fun sendReadReceipt() { // todo: 안됨
        Log.d("STOMP", chatList.last().chatsId.toString())
        viewModelScope.launch {
            try {
                stompSession?.send(
                    headers =
                        StompSendHeaders(
                            destination = "/app/chat/read/${chatRoomId.longValue}",
                            customHeaders =
                                mapOf(
                                    "content-type" to "application/json",
                                    "Authorization" to "Bearer ${loginRepository.accessToken() ?: ""}",
                                ),
                        ),
                    body =
                        FrameBody.Text(
                            Json.encodeToString(
                                ChatReadRequest.serializer(),
                                ChatReadRequest(chatList.last().chatsId),
                            ) + "\\0",
                        ),
                )
                Log.d("STOMP", chatList.last().chatsId.toString())
            } catch (e: Exception) {
                Log.e("STOMP", "읽은 메시지 ID 전송 실패: ${e.message}")
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            stompSession?.disconnect()
            Log.d("STOMP", "STOMP 연결 해제")
        }
    }

    fun fetchChatRoomProduct(
        chatroomId: Long,
        onComplete: (ChatRoomInfo?) -> Unit,
    ) {
        viewModelScope.launch {
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
        viewModelScope.launch {
            val newChatList =repository.chatList(chatroomId).chatList.map { it.toDomain() }
            chatList.clear()
            chatList.addAll(newChatList)
        }
    }

    fun fetchChatRoomList() {
        viewModelScope.launch {
            val newChatRoomList = repository.chatRoomList() // 여기서 ArrayList 반환
            chatRoomList.clear() // ✅ 기존 리스트 비우기
            chatRoomList.addAll(newChatRoomList) // ✅ 새로운 데이터 추가
        }
    }

    companion object {
        private const val TAG = "ChatViewModel"

        fun factory(
            repository: ChatRepository,
            loginRepository: LoginRepository,
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                ChatViewModel(
                    repository = repository,
                    loginRepository = loginRepository,
                )
            }
    }
}
