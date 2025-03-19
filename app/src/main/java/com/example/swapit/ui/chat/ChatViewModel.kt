package com.example.swapit.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.swapit.BuildConfig
import com.example.swapit.data.datasource.remote.RetrofitModule.okHttpClient
import com.example.swapit.data.datasource.remote.dto.request.chat.ChatReadRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.ChatRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatResponse
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.chat.Chat
import com.example.swapit.domain.model.chat.ChatRoom
import com.example.swapit.domain.model.chat.ChatRoomInfo
import com.example.swapit.domain.repository.ChatRepository
import com.example.swapit.domain.repository.LoginRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import com.example.swapit.ui.navigation.NavItem
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
    val chatRoomList = mutableStateOf(emptyList<ChatRoom>())
    val chatList = mutableStateOf(emptyList<Chat>())
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

    private fun connect() {
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
                            chatList.value += receivedChat.toDomain()
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
                connect()
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
                connect()
                subscribeToChatRoom()
                navController.navigate(NavItem.ChatRoom.screenRoute + "/$chatRoomId")
            } else {
                Log.e("ChatViewModel", "Chat room ID is not set. Failed to navigate.")
            }
        }
    }

    fun sendMessage(message: ChatRequest) {
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
        Log.d("STOMP", chatList.value.last().chatsId.toString())
        viewModelScope.launch {
            try {
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
                    body =
                        FrameBody.Text(
                            Json.encodeToString(
                                ChatReadRequest.serializer(),
                                ChatReadRequest(chatList.value.last().chatsId),
                            ) + "\\0",
                        ),
                )
                Log.d("STOMP", chatList.value.last().chatsId.toString())
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
            chatList.value = repository.chatList(chatroomId).chatList.map { it.toDomain() }
        }
    }

    fun fetchChatRoomList() {
        viewModelScope.launch {
            chatRoomList.value = repository.chatRoomList()
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
