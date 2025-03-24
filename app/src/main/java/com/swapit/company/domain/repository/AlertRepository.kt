package com.swapit.company.domain.repository

import com.swapit.company.data.datasource.RemoteAlertDataSource
import com.swapit.company.data.datasource.RemoteChatDataSource
import com.swapit.company.data.datasource.remote.ServiceModule
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.alert.AlertListResponse
import com.swapit.company.data.repository.DefaultAlertRepository
import com.swapit.company.data.repository.DefaultChatRepository

interface AlertRepository {
    suspend fun alertList(): BaseResponse<AlertListResponse>
    suspend fun readAlert(notificationsId: Long): BaseResponse<Unit>
    suspend fun fcmRestore(fcmToken: String): BaseResponse<Unit>
    suspend fun alertSetting(notificationEnabled: Boolean): BaseResponse<Unit>

    companion object {
        private var instance: AlertRepository? = null

        fun instance(): AlertRepository {
            if (instance == null) {
                instance =
                    DefaultAlertRepository(
                        remoteSource = RemoteAlertDataSource(ServiceModule.alertService))
            }
            return instance!!
        }
    }
}