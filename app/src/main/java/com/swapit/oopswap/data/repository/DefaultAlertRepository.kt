package com.swapit.oopswap.data.repository

import com.swapit.oopswap.data.datasource.RemoteAlertDataSource
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertListResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertSettingResponse
import com.swapit.oopswap.domain.repository.AlertRepository

class DefaultAlertRepository(
    private val remoteSource: RemoteAlertDataSource,
    private val onLogout: () -> Unit
) : AlertRepository {
    override suspend fun alertList(): Result<BaseResponse<AlertListResponse>> =
        safeApiCall(onLogout) {
            remoteSource.alertList()
        }

    override suspend fun readAlert(notificationsId: Long): Result<BaseResponse<Unit>> =
        safeApiCall(onLogout) {
            remoteSource.readAlert(notificationsId)
        }

    override suspend fun fcmRestore(fcmToken: String): Result<BaseResponse<Unit>> =
        safeApiCall(onLogout) {
            remoteSource.fcmRestore(fcmToken)
        }

    override suspend fun alertSetting(notificationEnabled: Boolean): Result<BaseResponse<Unit>> =
        safeApiCall(onLogout) {
            remoteSource.alertSetting(notificationEnabled)
        }

    override suspend fun alertSettingInfo(): Result<BaseResponse<AlertSettingResponse>> =
        safeApiCall(onLogout) {
            remoteSource.alertSettingInfo()
        }
}
