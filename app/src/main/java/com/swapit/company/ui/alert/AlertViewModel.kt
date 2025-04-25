package com.swapit.company.ui.alert

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.swapit.company.data.datasource.remote.StompModule
import com.swapit.company.data.mapper.toDomain
import com.swapit.company.domain.model.alert.Alert
import com.swapit.company.domain.repository.AlertRepository
import com.swapit.company.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AlertViewModel(
    private val repository: AlertRepository,
    private val stompModule: StompModule,
) : ViewModel() {
    val alertList = mutableStateOf(emptyList<Alert>())
    val alertSettingValue = mutableStateOf(false)

    fun connectAndMonitor() {
        stompModule.connectAndMonitor()
    }

    fun fetchAlertList() {
        viewModelScope.launch {
            alertList.value = repository.alertList().results.notifications.map { it.toDomain() }
        }
    }

    // 특정 관련 ID의 모든 알림 읽음 처리
    fun readAllAlertsByRelatedId(relatedId: Long?) {
        viewModelScope.launch {
            try {
                val alertsToRead = alertList.value.filter { it.relatedData == relatedId }
                alertsToRead.forEach { alert ->
                    repository.readAlert(alert.notificationsId)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to mark alerts as read for relatedId=$relatedId: ${e.message}")
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
                Log.d(TAG, "FCM 토큰: $newToken")
                repository.fcmRestore(savedToken!!)
                Log.d(TAG, "FCM 토큰 전송 완료")
            } catch (e: Exception) {
                Log.e(TAG, "FCM 토큰 가져오기 실패: ${e.message}")
            }
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
            repository: AlertRepository,
            stompModule: StompModule,
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                AlertViewModel(
                    repository = repository,
                    stompModule,
                )
            }
    }
}
