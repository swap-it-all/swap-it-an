package com.swapit.company.data.datasource.remote.service

import com.swapit.company.data.datasource.remote.dto.request.alert.AlertSettingRequest
import com.swapit.company.data.datasource.remote.dto.request.alert.FcmTokenRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.alert.AlertListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AlertService {
    @GET("api/user/notifications")
    suspend fun alertList(): BaseResponse<AlertListResponse>

    @PATCH("api/user/notifications/{notificationsId}/read")
    suspend fun readAlert(
        @Path("notificationsId") notificationsId: Long
    ): BaseResponse<Unit>

    @PUT("api/user/fcm")
    suspend fun fcmRestore(
        @Body fcmToken: FcmTokenRequest
    ): BaseResponse<Unit>

    @PATCH("api/user/notifications/setting")
    suspend fun alertSetting(
        @Body notificationEnabled: AlertSettingRequest
    ): BaseResponse<Unit>
}