package com.swapit.company.data.repository

import com.swapit.company.data.datasource.RemoteAlertDataSource
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.alert.AlertListResponse
import com.swapit.company.domain.repository.AlertRepository

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

}