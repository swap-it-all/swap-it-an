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
