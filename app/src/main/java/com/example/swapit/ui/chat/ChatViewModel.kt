package com.example.swapit.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.swapit.BuildConfig
import com.example.swapit.SwapItApplication.Companion.appContext
import com.example.swapit.data.datasource.local.LocalLoginDataSource
import com.example.swapit.data.datasource.remote.LoginServiceHolder
import com.example.swapit.data.datasource.remote.dto.request.chat.ChatReadRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.ChatRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatResponse
import com.example.swapit.data.datasource.remote.interceptor.AuthAuthenticator
import com.example.swapit.data.datasource.remote.interceptor.AuthInterceptor
import com.example.swapit.data.datasource.remote.interceptor.LoggingInterceptor
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.chat.Chat
import com.example.swapit.domain.model.chat.ChatRoom
import com.example.swapit.domain.model.chat.ChatRoomInfo
import com.example.swapit.domain.repository.ChatRepository
import com.example.swapit.domain.repository.LoginRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import com.example.swapit.ui.navigation.NavItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.frame.FrameBody
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.util.concurrent.TimeUnit

class ChatViewModel(private val repository: ChatRepository, loginRepository: LoginRepository,) : ViewModel()  {
    val chatRoomList  = mutableStateOf(emptyList<ChatRoom>())
    val chatList = mutableStateOf(emptyList<Chat>())
    val chatRoomId = mutableLongStateOf(0L)
    val goodsId = mutableLongStateOf(0L)
    val tradesId = mutableLongStateOf(0L)
    private val loginServiceHolder = LoginServiceHolder()
    private val wsClient = OkHttpWebSocketClient(okHttpClient())
    private val stompClient = StompClient(wsClient)
    private var stompSession: StompSession? = null
    private var accessToken = loginRepository.accessToken() ?: ""
    val chatRoomProduct = mutableStateOf(ChatRoomInfo(
        goodsId = 1,
        imageUrl = "",
        title = "",
        price = 0,
        nickname = "",
        category = "",
        usersId = 1
    ))

    private fun okHttpClient(): OkHttpClient {
        val localLoginDataSource = LocalLoginDataSource(appContext)
        val authenticator = AuthAuthenticator(loginServiceHolder, LocalLoginDataSource(appContext))
        return OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor(localLoginDataSource))
            .authenticator(authenticator)
            .addInterceptor(LoggingInterceptor.create())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private fun connect() {
        Log.d("STOMP", "connect() 연결 중")
        viewModelScope.launch {
            try {
                stompSession = stompClient.connect(
                    BuildConfig.SWAP_IT_BASE_URL.replace(
                        "http",
                        "ws"
                    ) + "ws",
                    customStompConnectHeaders = mapOf("Authorization" to accessToken),
                    host = "localhost"
                )
                Log.d("STOMP", "connect() 연결 성공")
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
                val messageFlow = stompSession!!.subscribe(
                    StompSubscribeHeaders(destination = "/topic/chat/${chatRoomId.longValue}")
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

    fun initiateChatFlow(goodsId: Long, navController: NavController) {
        viewModelScope.launch {
            val goodsIdRequest = GoodsIdRequest(goodsId)
            val chatRoomId = createChatRoomSync(goodsIdRequest)
            this@ChatViewModel.chatRoomId.longValue = chatRoomId

            if (chatRoomId != 0L) {
                connect()
                subscribeToChatRoom()
                navController.navigate(NavItem.ChatRoom.screenRoute + "/$chatRoomId")
            } else {
                Log.e("ChatViewModel", "Chat room ID is not set. Failed to navigate.")
            }
        }
    }

    fun initiateChatSwapFlow(tradesId: Long, navController: NavController) {
        viewModelScope.launch {
            val tradesIdRequest = TradesIdRequest(tradesId)
            val chatRoomId = createChatRoomTradeSync(tradesIdRequest)
            this@ChatViewModel.chatRoomId.longValue = chatRoomId

            if (chatRoomId != 0L) {
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
                stompSession?.send(
                    headers = StompSendHeaders(
                        destination = "/app/chat/${chatRoomId.longValue}"
                    ),
                    body = FrameBody.Text(Json.encodeToString(ChatRequest.serializer(), message) + "\\0")
                )
                Log.d("STOMP", "메시지 전송 성공 채팅방 아이디: ${chatRoomId.longValue}")
                Log.d("STOMP", "메시지 전송 성공: $message")
            } catch (e: Exception) {
                Log.e("STOMP", "메시지 전송 실패: ${e.message}")
            }
        }
    }

    fun sendReadReceipt() {
        viewModelScope.launch {
            try {
                stompSession?.send(
                    headers = StompSendHeaders(
                        destination = "/app/chat/read/${chatRoomId.longValue}"
                    ),
                    body = FrameBody.Text(
                        Json.encodeToString(
                            ChatReadRequest.serializer(),ChatReadRequest(chatList.value.last().chatsId)
                        ) + "\\0"
                    )
                )
                Log.d("STOMP", "읽은 메시지 ID 전송 성공")
            } catch (e: Exception) {
                Log.e("STOMP", "읽은 메시지 ID 전송 실패: ${e.message}")
            }
        }
    }
    fun disconnect() {
        runBlocking {
            stompSession?.disconnect()
            Log.d("STOMP", "STOMP 연결 해제")
        }
    }


    fun fetchChatRoomProduct(chatroomId: Long) {
        viewModelScope.launch {
            chatRoomProduct.value = repository.chatRoomInfo(chatroomId)
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
                    loginRepository = loginRepository
                )
            }
    }
}