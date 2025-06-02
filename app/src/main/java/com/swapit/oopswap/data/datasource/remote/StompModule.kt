package com.swapit.oopswap.data.datasource.remote

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.app.NotificationCompat
import com.swapit.oopswap.BuildConfig
import com.swapit.oopswap.R
import com.swapit.oopswap.data.datasource.local.model.alert.AlertType
import com.swapit.oopswap.data.datasource.remote.RetrofitModule.okHttpClient
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.ChatReadRequest
import com.swapit.oopswap.data.datasource.remote.dto.request.chat.ChatRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.NotificationResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.chat.ChatResponse
import com.swapit.oopswap.data.mapper.toDomain
import com.swapit.oopswap.domain.model.chat.Chat
import com.swapit.oopswap.domain.repository.LoginRepository
import com.swapit.oopswap.ui.base.AlertNotifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
import kotlin.coroutines.cancellation.CancellationException

class StompModule(
    private val loginRepository: LoginRepository,
    private val application: Application,
) {
    private val subscriptionCounter = AtomicInteger(0)
    private val subscriptionIds = ConcurrentHashMap<Long, String>()
    private val subscriptionJobs = ConcurrentHashMap<Long, Job>()
    private val wsClient by lazy { OkHttpWebSocketClient(okHttpClient()) }
    private val stompClient = StompClient(wsClient)
    private var stompSession: StompSession? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun connectAndMonitor() {
        scope.launch {
            while (true) {
                try {
                    if (stompSession == null) {
                        Log.d(TAG, "STOMP 연결이 끊어져 다시 연결 시도...")
                        disconnect()
                        connect {
                            subscribeAlert()
                        }
                    }
                    delay(Duration.ofMillis(5000))
                } catch (e: Exception) {
                    Log.e(TAG, "STOMP 재연결 실패: ${e.message}")
                }
            }
        }
    }

    private fun connect(onConnected: (() -> Unit)? = null) {
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
                Log.d(TAG, "웹소켓 연결 성공")
                delay(Duration.ofMillis(1000))
                onConnected?.invoke()
            } catch (e: Exception) {
                Log.e(TAG, "웹소켓 연결 실패: ${e.message}")
                stompSession = null
            }
        }
    }

    private fun subscribeAlert() {
        scope.launch {
            if (stompSession == null) {
                Log.e("STOMP", "알림 구독 연결 실패")
                return@launch
            }
            try {
                val messageFlow =
                    stompSession!!.subscribe(
                        StompSubscribeHeaders(
                            destination = "/user/queue/notifications",
                            id = "alert-sub",
                        ),
                    )

                Log.d("STOMP", "알림 구독 성공!")

                messageFlow.collect { frame ->
                    Log.d("STOMP", "알림 수신: ${frame.bodyAsText}")
                    frame.bodyAsText?.let { jsonMessage ->
                        try {
                            val notification = Json.decodeFromString<NotificationResponse>(jsonMessage)
                            handleNotification(notification)
                        } catch (e: Exception) {
                            Log.e("STOMP", "알림 처리 실패: ${e.message}")
                        }
                    }
                }
            } catch (e: CancellationException) {
                Log.d("STOMP", "알림 구독 취소")
            } catch (e: Exception) {
                Log.e("STOMP", "알림 구독 실패: ${e.message}")
            }
        }
    }

    private fun handleNotification(notification: NotificationResponse) {
        Log.d("Notification", "알림 수신: ${notification.body}")

        val type = runCatching { AlertType.valueOf(notification.type) }.getOrElse { AlertType.CHAT }
        AlertNotifier.notify(notification.body, type)
//        showNotification(notification) // 기존 시스템 알림은 유지
    }

    private fun showNotification(notification: NotificationResponse) {
        val notificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder =
            NotificationCompat
                .Builder(application, "swapit_alert_channel")
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setSmallIcon(R.drawable.ic_bell)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        notificationManager.notify(notification.notificationsId.toInt(), notificationBuilder.build())
    }

    fun subscribeToChatRoom(
        chatRoomId: Long,
        chatList: SnapshotStateList<Chat>,
    ): SnapshotStateList<Chat> {
        val job =
            scope.launch(SupervisorJob()) {
                if (stompSession == null) {
                    Log.e("STOMP", "채팅방 구독 연결 실패")
                    disconnect()
                    connect()
                    return@launch
                }
                try {
                    val subId = "chat-sub-${subscriptionCounter.incrementAndGet()}"
                    subscriptionIds[chatRoomId] = subId
                    val messageFlow =
                        stompSession!!.subscribe(
                            StompSubscribeHeaders(
                                destination = "/topic/chat/$chatRoomId",
                                id = subId,
                            ),
                        )

                    Log.d("STOMP", "채팅방 구독 성공 : $chatRoomId")

                    messageFlow.collect { frame ->
                        Log.d("STOMP", "채팅 메시지 수신: ${frame.bodyAsText}")
                        frame.bodyAsText?.let { jsonMessage ->
                            try {
                                val receivedChat = Json.decodeFromString<ChatResponse>(jsonMessage)
                                if (chatList.any { it.chatsId == receivedChat.chatsId }) return@let
                                chatList.add(receivedChat.toDomain())
                            } catch (e: Exception) {
                                Log.e("STOMP", "메시지 처리 실패: ${e.message}")
                            }
                        }
                    }
                } catch (e: CancellationException) {
                    Log.d("STOMP", "채팅방 구독 취소: $chatRoomId")
                } catch (e: Exception) {
                    Log.e("STOMP", "채팅방 구독 실패: $e")
                }
            }
        subscriptionJobs[chatRoomId] = job
        return chatList
    }

    fun unsubscribeFromChatRoom(chatRoomId: Long) {
        scope.launch {
            subscriptionIds.remove(chatRoomId)
            val job = subscriptionJobs.remove(chatRoomId)
            job?.cancel()
            Log.d("STOMP", "채팅방 구독 해지: chatRoomId=$chatRoomId")
        }
    }

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
        if (message.content == "") return
        scope.launch {
            try {
                if (isAccessTokenExpired()) {
                    val newTokens = loginRepository.refresh(loginRepository.refreshToken()!!)
                    connect()
                    subscribeAlert()
                    Log.d(TAG, "액세스 토큰 갱신 성공: ${newTokens.accessToken}")
                }
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
                    body = FrameBody.Text(Json.encodeToString(ChatRequest.serializer(), message) + "\\0"),
                )
                kotlinx.coroutines.delay(100)
                Log.d(TAG, "채팅 메시지 전송 성공: $message")
            } catch (e: Exception) {
                Log.e(TAG, "채팅 메시지 전송 실패: ${e.message}")
            }
        }
    }

    fun disconnect() {
        scope.launch {
            try {
                subscriptionIds.keys.forEach { chatRoomId -> unsubscribeFromChatRoom(chatRoomId) }
                stompSession?.disconnect()
                stompSession = null
                subscriptionIds.clear()
                Log.d(TAG, "웹소켓 연결 해제 및 구독 해지 완료")
            } catch (e: Exception) {
                Log.e(TAG, "웹소켓 연결 해제 실패: ${e.message}")
            }
        }
    }

    fun sendReadReceipt(
        chatRoomId: Long,
        chatList: List<Chat>,
    ) {
        if (chatList.isEmpty()) return
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
                            Json.encodeToString(ChatReadRequest.serializer(), ChatReadRequest(chatList.first().chatsId)) + "\\0",
                        ),
                )
                Log.d("STOMP", "읽음 처리 전송 성공: ${chatList.first().chatsId}")
            } catch (e: Exception) {
                Log.e("STOMP", "읽음 처리 전송 실패: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "StompModule"
    }
}
