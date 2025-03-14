package com.example.swapit.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.BuildConfig
import com.example.swapit.SwapItApplication.Companion.appContext
import com.example.swapit.data.datasource.local.LocalLoginDataSource
import com.example.swapit.data.datasource.local.model.post.CategoryOption
import com.example.swapit.data.datasource.remote.LoginServiceHolder
import com.example.swapit.data.datasource.remote.dto.request.chat.ChatRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.example.swapit.data.datasource.remote.dto.request.chat.TradesIdRequest
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatResponse
import com.example.swapit.data.datasource.remote.interceptor.AuthAuthenticator
import com.example.swapit.data.datasource.remote.interceptor.AuthInterceptor
import com.example.swapit.data.datasource.remote.interceptor.LoggingInterceptor
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.chat.Chat
import com.example.swapit.domain.model.chat.ChatList
import com.example.swapit.domain.model.chat.ChatRoom
import com.example.swapit.domain.model.chat.ChatRoomProduct
import com.example.swapit.domain.repository.ChatRepository
import com.example.swapit.domain.repository.ProductRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import com.example.swapit.ui.shopping.detail.ShoppingDetailViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.frame.FrameBody
import org.hildan.krossbow.stomp.frame.StompFrame
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.util.concurrent.TimeUnit

class ChatViewModel(private val repository: ChatRepository) : ViewModel()  {
    val chatRoomList  = mutableStateOf(emptyList<ChatRoom>())
    val chatList = mutableStateOf(emptyList<Chat>())
    val chatRoomId = mutableLongStateOf(0L)
    val goodsId = mutableLongStateOf(0L)
    val tradesId = mutableLongStateOf(0L)
    val chatRoomProduct = mutableStateOf(ChatRoomProduct(
        nickname = "",
        goodsId = 0,
        title = "",
        category = "",
        price = 0,
        imageUrl = ""
    ))



    private val loginServiceHolder = LoginServiceHolder()

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
    private val wsClient = OkHttpWebSocketClient(okHttpClient())
    private val stompClient = StompClient(wsClient)
    private var stompSession: StompSession? = null
    fun connect() {
        runBlocking {
            try {
                // WebSocket 엔드포인트에 연결
                stompSession = stompClient.connect(
                    BuildConfig.SWAP_IT_BASE_URL.replace(
                        "http",
                        "ws"
                    ) + "/websocket"
                )
                Log.d("STOMP", "connect() 연결 성공")
            } catch (e: Exception) {
                Log.e("STOMP", "connect() 연결 실패: ${e.message}")
            }
        }
    }

    fun subscribeToChatRoom() {
        runBlocking {
            if (stompSession == null) {
                Log.e("STOMP", "subscribeToChatRoom() 연결 실패")
            }
            stompSession?.subscribe(
                StompSubscribeHeaders(
                    destination = "/topic/chat/${chatRoomId.longValue}",
                )
            )
        }
    }

    fun sendMessage(message: ChatRequest) {
        runBlocking {
            try {
                stompSession?.send(
                    headers = StompSendHeaders(
                        destination = "/app/chat/${chatRoomId.longValue}",
                    ),
                    body = FrameBody.Text(Json.encodeToString(ChatRequest.serializer(), message))
                )
                Log.d("STOMP", "메시지 전송 성공 채팅방 아이디: ${chatRoomId.longValue}")
                Log.d("STOMP", "메시지 전송 성공: $message")
            } catch (e: Exception) {
                Log.e("STOMP", "메시지 전송 실패: ${e.message}")
            }
        }
    }

    fun disconnect() {
        runBlocking {
            stompSession?.disconnect()
            Log.d("STOMP", "STOMP 연결 해제")
        }
    }

    fun createChatRoom(goodsId: GoodsIdRequest) {
        viewModelScope.launch {
            chatRoomId.longValue = repository.createChatRoom(goodsId).results
        }
    }

    fun createSwapChatRoom(tradesId: TradesIdRequest) {
        viewModelScope.launch {
            chatRoomId.longValue = repository.createSwapChatRoom(tradesId).results
        }
    }

    fun fetchChatRoomProduct(chatroomId: Long) {
        viewModelScope.launch {
            chatRoomProduct.value = repository.chatRoomProduct(chatroomId)
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
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                ChatViewModel(
                    repository = repository,
                )
            }
    }
}