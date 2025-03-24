package com.swapit.company.data.datasource

import com.swapit.company.data.datasource.remote.dto.request.alert.FcmTokenRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.alert.AlertListResponse
import com.swapit.company.data.datasource.remote.service.AlertService

class RemoteAlertDataSource(private val service: AlertService) {
    suspend fun alertList(): BaseResponse<AlertListResponse> = service.alertList()
    suspend fun readAlert(notificationsId: Long): BaseResponse<Unit> = service.readAlert(notificationsId)
    suspend fun fcmRestore(fcmToken: String): BaseResponse<Unit> = service.fcmRestore(FcmTokenRequest(fcmToken))
}