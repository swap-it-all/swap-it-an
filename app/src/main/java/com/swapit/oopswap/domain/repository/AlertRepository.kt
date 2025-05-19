package com.swapit.oopswap.domain.repository

import com.swapit.oopswap.data.datasource.RemoteAlertDataSource
import com.swapit.oopswap.data.datasource.remote.ServiceModule
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertListResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertSettingResponse
import com.swapit.oopswap.data.repository.DefaultAlertRepository

interface AlertRepository {
    suspend fun alertList(): Result<BaseResponse<AlertListResponse>>

    suspend fun readAlert(notificationsId: Long): Result<BaseResponse<Unit>>

    suspend fun fcmRestore(fcmToken: String): Result<BaseResponse<Unit>>

    suspend fun alertSetting(notificationEnabled: Boolean): Result<BaseResponse<Unit>>

    suspend fun alertSettingInfo(): Result<BaseResponse<AlertSettingResponse>>

    companion object {
        private var instance: AlertRepository? = null

        fun instance(onLogout: () -> Unit = {}): AlertRepository {
            if (instance == null) {
                instance =
                    DefaultAlertRepository(
                        remoteSource = RemoteAlertDataSource(ServiceModule.alertService),
                        onLogout = onLogout
                    )
            }
            return instance!!
        }
    }
}
