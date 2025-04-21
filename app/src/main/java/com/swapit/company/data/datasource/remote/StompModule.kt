package com.swapit.company.data.datasource.remote

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.app.NotificationCompat
import com.swapit.company.BuildConfig
import com.swapit.company.R
import com.swapit.company.data.datasource.remote.RetrofitModule.okHttpClient
import com.swapit.company.data.datasource.remote.RetrofitModule.okHttpWebSocketClient
import com.swapit.company.data.datasource.remote.dto.request.chat.ChatReadRequest
import com.swapit.company.data.datasource.remote.dto.request.chat.ChatRequest
import com.swapit.company.data.datasource.remote.dto.response.alert.NotificationResponse
import com.swapit.company.data.datasource.remote.dto.response.chat.ChatResponse
import com.swapit.company.data.mapper.toDomain
import com.swapit.company.domain.model.chat.Chat
import com.swapit.company.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.serialization.json.Json
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.frame.FrameBody
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import org.json.JSONObject
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class StompModule(
    private val loginRepository: LoginRepository,
    private val application: Application
) {
    private val subscriptionCounter = AtomicInteger(0)
    private val subscriptionIds = ConcurrentHashMap<Long, String>() // 채팅방 구독 ID 저장
    private val wsClient by lazy {
        OkHttpWebSocketClient(okHttpWebSocketClient())
    }
    private val stompClient = StompClient(wsClient)
    private var stompSession: StompSession? = null

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun connectAndMonitor() {
        scope.launch {
            while (true) {
                try {
                    if (stompSession == null) {
                        Log.d(TAG, "STOMP 연결이 끊어져 다시 연결 시도...")
                        disconnect() // 기존 세션 완전히 종료
                        connect() // 재연결 시도
                        delay(Duration.ofMillis(3000)) // 재연결 후 잠시 대기
                        subscribeAlert() // 재연결 후 다시 구독
                    }
                    delay(Duration.ofMillis(5000)) // 5초마다 체크
                } catch (e: Exception) {
                    Log.e(TAG, "STOMP 재연결 실패: ${e.message}")
                }
            }
        }
    }

    private fun connect() {
        scope.launch {
            try {
                stompSession =
                    stompClient.connect(
                        BuildConfig.SWAP_IT_BASE_URL.replace("http", "ws") + "ws",
                        customStompConnectHeaders =
                        mapOf(
                            "Authorization" to "Bearer ${loginRepository.accessToken() ?: ""}",
                            "accept-version" to "1.2",
                        ),
                    )
                Log.d(TAG, "STOMP 연결 성공")
            } catch (e: Exception) {
                Log.e(TAG, "STOMP 연결 실패: ${e.message}")
                stompSession = null // 실패 시 세션 초기화
            }
        }
    }

    private fun subscribeAlert() {
        scope.launch {
            if (stompSession == null) {
                Log.e("STOMP", "alert 구독 연결 실패")
                return@launch
            }
            try {
                val messageFlow =
                    stompSession!!.subscribe(
                        StompSubscribeHeaders(
                            destination = "/user/queue/notifications",
                            id = "sub-0",
                        ),
                    )
                messageFlow.collect { frame ->
                    Log.d("STOMP", "알림 수신: ${frame.bodyAsText}")
                    frame.bodyAsText?.let { jsonMessage ->
                        try {
                            val notification =
                                Json.decodeFromString<NotificationResponse>(jsonMessage)
                            handleNotification(notification)
                        } catch (e: Exception) {
                            Log.e("STOMP", "알림 처리 실패: ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("STOMP", "subscribe 실패: ${e.message}")
            }
        }
    }

    private fun handleNotification(notification: NotificationResponse) {
        Log.d("Notification", "알림 수신: ${notification.message}")

        scope.launch(Dispatchers.Main) {
            Toast.makeText(application, notification.message, Toast.LENGTH_SHORT).show()
        }
        showNotification(notification)
    }

    private fun showNotification(notification: NotificationResponse) {
        val notificationManager =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder =
            NotificationCompat.Builder(application, "default_channel")
                .setContentTitle("새로운 알림")
                .setContentText(notification.message)
                .setSmallIcon(R.drawable.ic_bell)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(
            notification.notificationsId.toInt(),
            notificationBuilder.build(),
        )
    }

    fun subscribeToChatRoom(
        chatRoomId: Long,
        chatList: SnapshotStateList<Chat>, // SnapshotStateList로 받기
    ): SnapshotStateList<Chat> {
        scope.launch(SupervisorJob()) {
            if (stompSession == null) {
                Log.e("STOMP", "subscribeToChatRoom() 연결 실패")
                disconnect() // 기존 세션 완전히 종료
                connect() // 새로운 세션 연결
                return@launch
            }
            try {
                val subId = "sub-${subscriptionCounter.incrementAndGet()}"
                subscriptionIds[chatRoomId] = subId

                val messageFlow =
                    stompSession!!.subscribe(
                        StompSubscribeHeaders(
                            destination = "/topic/chat/$chatRoomId",
                            id = subId,
                        ),
                    )
                messageFlow.collect { frame ->
                    Log.d("STOMP", "수신 메시지: ${frame.bodyAsText}")
                    frame.bodyAsText?.let { jsonMessage ->
                        try {
                            val receivedChat = Json.decodeFromString<ChatResponse>(jsonMessage)
                            if (chatList.any { it.chatsId == receivedChat.chatsId }) return@let
                            chatList.add(receivedChat.toDomain()) // SnapshotStateList에 추가
                        } catch (e: Exception) {
                            Log.e("STOMP", "메시지 처리 실패: ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("STOMP", "subscribeToChatRoom 실패: ${e}")
            }
        }
        return chatList // 전달받은 chatList를 반환
    }


    fun unsubscribeFromChatRoom(chatRoomId: Long) {
        scope.launch {
            val subId = subscriptionIds.remove(chatRoomId) ?: return@launch // 구독 ID 찾기
            try {
                stompSession?.send(
                    headers =
                    StompSendHeaders(
                        destination = "/topic/chat/$chatRoomId",
                        customHeaders =
                        mapOf(
                            "id" to subId,
                        ),
                    ),
                    body = FrameBody.Text(""),
                )
                Log.d("STOMP", "구독 해지 성공: chatRoomId=$chatRoomId, subId=$subId")
            } catch (e: Exception) {
                Log.e("STOMP", "구독 해지 실패: ${e.message}")
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

    fun sendMessage(
        message: ChatRequest,
        chatRoomId: Long,
    ) {
        if (message.content == "") {
            return
        }
        scope.launch {
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
                        destination = "/app/chat/$chatRoomId",
                        customHeaders =
                        mapOf(
                            "content-type" to "application/json",
                            "Authorization" to "Bearer ${loginRepository.accessToken() ?: ""}",
                        ),
                    ),
                    body = FrameBody.Text(
                        Json.encodeToString(
                            ChatRequest.serializer(),
                            message
                        ) + "\\0"
                    ),
                )
                kotlinx.coroutines.delay(100)
                Log.d(TAG, "메시지 전송 성공: $message")
            } catch (e: Exception) {
                Log.e(TAG, "메시지 전송 실패: ${e.message}")
            }
        }
    }

    fun disconnect() {
        scope.launch {
            try {
                subscriptionIds.keys.forEach { chatRoomId ->
                    unsubscribeFromChatRoom(chatRoomId)
                }
                stompSession?.disconnect() // 기존 세션 종료
                stompSession = null // 세션 초기화
                subscriptionIds.clear()
                Log.d(TAG, "STOMP 연결 해제 및 모든 구독 해지")
            } catch (e: Exception) {
                Log.e(TAG, "STOMP 연결 해제 실패: ${e.message}")
            }
        }
    }

    fun sendReadReceipt(
        chatRoomId: Long,
        chatList: List<Chat>,
    ) {
        if (chatList.isEmpty()) return
        Log.d("STOMP", chatList.last().chatsId.toString())
        scope.launch {
            try {
                stompSession?.send(
                    headers =
                    StompSendHeaders(
                        destination = "/app/chat/read/$chatRoomId",
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
                            ChatReadRequest(chatList.first().chatsId),
                        ) + "\\0",
                    ),
                )
                Log.d("STOMP", chatList.first().chatsId.toString())
            } catch (e: Exception) {
                Log.e("STOMP", "읽은 메시지 ID 전송 실패: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "StompModule"
    }
}
