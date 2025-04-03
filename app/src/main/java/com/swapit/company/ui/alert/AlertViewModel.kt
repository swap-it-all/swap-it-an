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
import kotlinx.serialization.json.Json
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient

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
                                "accept-version" to "1.1",
                                "content-length" to "0",
                            ),
                    )
            } catch (e: Exception) {
                Log.e("STOMP", "connect() 연결 실패: ${e.message}")
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

    fun initiateAlert() {
        viewModelScope.launch {
            try {
                connect() // 연결 시도
                subscribeAlert() // 구독 시도
            } catch (e: Exception) {
                Log.e(TAG, "Alert 연결 및 구독 실패: ${e.message}")
            }
        }
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
        val sharedPref = application.getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
        val fcmToken = sharedPref.getString("fcm_token", null)

        if (fcmToken != null) {
            viewModelScope.launch {
                repository.fcmRestore(fcmToken)
            }
        } else {
            Log.e("AlertViewModel", "FCM 토큰이 없습니다.")
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
