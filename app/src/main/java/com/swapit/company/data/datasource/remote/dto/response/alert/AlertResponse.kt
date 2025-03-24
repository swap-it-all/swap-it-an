package com.swapit.company.data.datasource.remote.dto.response.alert

import android.app.Notification
import kotlinx.serialization.Serializable


@Serializable
data class AlertResponse(
    val notificationsId: Long,
    val type: String,
    val title: String,
    val body: String,
    val deeplink: String,
    val createdAt: String
)
