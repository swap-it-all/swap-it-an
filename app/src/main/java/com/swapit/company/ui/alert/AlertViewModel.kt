package com.swapit.company.ui.alert

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.swapit.company.BuildConfig
import com.swapit.company.R
import com.swapit.company.data.datasource.remote.RetrofitModule.okHttpClient
import com.swapit.company.data.datasource.remote.dto.response.alert.NotificationResponse
import com.swapit.company.data.mapper.toDomain
import com.swapit.company.domain.model.alert.Alert
import com.swapit.company.domain.repository.AlertRepository
import com.swapit.company.domain.repository.LoginRepository
import com.swapit.company.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.time.delay
import kotlinx.serialization.json.Json
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.time.Duration

class AlertViewModel(
    private val application: Application,
    private val repository: AlertRepository,
    private val loginRepository: LoginRepository,
) : ViewModel() {
    val alertList = mutableStateOf(emptyList<Alert>())
    val alertSettingValue = mutableStateOf(false)
    private val wsClient by lazy {
        OkHttpWebSocketClient(okHttpClient())
    }
    private val stompClient = StompClient(wsClient)
    private var stompSession: StompSession? = null

    fun connectAndMonitor() {
        viewModelScope.launch {
            while (true) {
                try {
                    if (stompSession == null) {
                        Log.d(TAG, "STOMP 연결이 끊어져 다시 연결 시도...")
                        connect() // 재연결 시도
                        delay(Duration.ofMillis(3000)) // 재연결 후 잠시 대기
                        if (stompSession != null) {
                            subscribeAlert() // 재연결 후 다시 구독
                        }
                    }
                    delay(Duration.ofMillis(5000)) // 5초마다 체크
                } catch (e: Exception) {
                    Log.e(TAG, "STOMP 재연결 실패: ${e.message}")
                }
            }
        }
    }

    // 기존 connect() 함수 수정
    private fun connect() {
        viewModelScope.launch {
            try {
                stompSession =
                    stompClient.connect(
                        BuildConfig.SWAP_IT_BASE_URL.replace("http", "ws") + "ws",
                        customStompConnectHeaders =
                            mapOf(
                                "Authorization" to "Bearer ${loginRepository.accessToken() ?: ""}",
                                "accept-version" to "1.1",
                                "content-length" to "0",
                            ),
                    )
                Log.d(TAG, "STOMP 연결 성공")
            } catch (e: Exception) {
                Log.e(TAG, "STOMP 연결 실패: ${e.message}")
            }
        }
    }

    private fun subscribeAlert() {
        viewModelScope.launch {
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
                            customHeaders = mapOf("content-length" to "0"),
                        ),
                    )
                messageFlow.collect { frame ->
                    Log.d("STOMP", "알림 메시지 수신: ${frame.bodyAsText}")
                    frame.bodyAsText?.let { jsonMessage ->
                        try {
                            // JSON 메시지 파싱
                            val notification =
                                Json.decodeFromString<NotificationResponse>(jsonMessage)
                            handleNotification(notification)
                        } catch (e: Exception) {
                            Log.e("STOMP", "알림 메시지 처리 실패: ${e.message}")
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

        // 알림 메시지를 UI에 표시하거나 알림(NotificationManager)을 통해 사용자에게 알립니다.
        // 예:
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

    fun fetchAlertList() {
        viewModelScope.launch {
            alertList.value = repository.alertList().results.notifications.map { it.toDomain() }
        }
    }

    fun readAlert(alertId: Long) {
        viewModelScope.launch {
            repository.readAlert(alertId)
        }
    }

    fun fcmRestore(application: Application) {
        viewModelScope.launch {
            try {
                val sharedPref = application.getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
                val savedToken = sharedPref.getString("fcm_token", null)

                // Firebase에서 최신 FCM 토큰 가져오기
                val newToken = FirebaseMessaging.getInstance().token.await()
                Log.d("AlertViewModel", "새로운 FCM 토큰: $newToken")

                if (newToken.isNotEmpty() && newToken != savedToken) {
                    // 최신 토큰이 기존 토큰과 다르면 서버에 전송
                    repository.fcmRestore(newToken)

                    // SharedPreferences에 최신 토큰 저장
                    sharedPref.edit().putString("fcm_token", newToken).apply()
                    Log.d("AlertViewModel", "FCM 토큰 업데이트 완료")
                } else {
                    Log.d("AlertViewModel", "FCM 토큰 변경 없음, 서버 전송 생략")
                }
            } catch (e: Exception) {
                Log.e("AlertViewModel", "FCM 토큰 가져오기 실패: ${e.message}")
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            stompSession?.disconnect()
        }
    }

    fun alertSetting() {
        viewModelScope.launch {
            repository.alertSetting(alertSettingValue.value)
        }
    }

    fun alertSettingInfo() {
        viewModelScope.launch {
            alertSettingValue.value = repository.alertSettingInfo().results.notificationEnabled
        }
    }

    companion object {
        private const val TAG = "AlertViewModel"

        fun factory(
            application: Application,
            repository: AlertRepository,
            loginRepository: LoginRepository,
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                AlertViewModel(
                    application = application,
                    repository = repository,
                    loginRepository = loginRepository,
                )
            }
    }
}
