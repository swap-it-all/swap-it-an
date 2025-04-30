package com.swapit.oopswap.data.datasource

import com.swapit.oopswap.data.datasource.remote.dto.request.alert.AlertSettingRequest
import com.swapit.oopswap.data.datasource.remote.dto.request.alert.FcmTokenRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertListResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertSettingResponse
import com.swapit.oopswap.data.datasource.remote.service.AlertService

class RemoteAlertDataSource(private val service: AlertService) {
    suspend fun alertList(): BaseResponse<AlertListResponse> = service.alertList()

    suspend fun readAlert(notificationsId: Long): BaseResponse<Unit> = service.readAlert(notificationsId)

    suspend fun fcmRestore(fcmToken: String): BaseResponse<Unit> = service.fcmRestore(FcmTokenRequest(fcmToken))

    suspend fun alertSetting(notificationEnabled: Boolean): BaseResponse<Unit> =
        service.alertSetting(
            AlertSettingRequest(notificationEnabled),
        )

    suspend fun alertSettingInfo(): BaseResponse<AlertSettingResponse> = service.alertSettingInfo()
}
