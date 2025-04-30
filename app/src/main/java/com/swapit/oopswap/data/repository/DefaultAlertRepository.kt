package com.swapit.oopswap.data.repository

import com.swapit.oopswap.data.datasource.RemoteAlertDataSource
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertListResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertSettingResponse
import com.swapit.oopswap.domain.repository.AlertRepository

class DefaultAlertRepository(private val remoteSource: RemoteAlertDataSource) : AlertRepository {
    override suspend fun alertList(): BaseResponse<AlertListResponse> {
        return remoteSource.alertList()
    }

    override suspend fun readAlert(notificationsId: Long): BaseResponse<Unit> {
        return remoteSource.readAlert(notificationsId)
    }

    override suspend fun fcmRestore(fcmToken: String): BaseResponse<Unit> {
        return remoteSource.fcmRestore(fcmToken)
    }

    override suspend fun alertSetting(notificationEnabled: Boolean): BaseResponse<Unit> {
        return remoteSource.alertSetting(notificationEnabled)
    }

    override suspend fun alertSettingInfo(): BaseResponse<AlertSettingResponse> {
        return remoteSource.alertSettingInfo()
    }
}
