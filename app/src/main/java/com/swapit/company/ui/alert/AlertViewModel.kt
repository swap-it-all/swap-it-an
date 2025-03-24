package com.swapit.company.ui.alert

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swapit.company.data.mapper.toDomain
import com.swapit.company.domain.model.alert.Alert
import com.swapit.company.domain.repository.AlertRepository
import com.swapit.company.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class AlertViewModel(private val repository: AlertRepository) : ViewModel() {
    val alertList = mutableStateOf(emptyList<Alert>())
    val alertSettingValue = mutableStateOf(false)
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
    fun fcmRestore(fcmToken: String) {
        viewModelScope.launch {
            repository.fcmRestore(fcmToken)
        }
    }
    fun alertSetting() {
        viewModelScope.launch {
            repository.alertSetting(alertSettingValue.value)
        }
    }
    companion object {
        private const val TAG = "AlertViewModel"

        fun factory(
            repository: AlertRepository,
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                AlertViewModel(
                    repository = repository,
                )
            }
    }
}