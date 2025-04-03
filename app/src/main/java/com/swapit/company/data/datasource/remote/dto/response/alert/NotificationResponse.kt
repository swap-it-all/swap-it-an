package com.swapit.company.data.datasource.remote.dto.response.alert

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val notificationsId: Long,
    val type: String,
    val url: String,
    val message: String,
    val createdAt: String
)