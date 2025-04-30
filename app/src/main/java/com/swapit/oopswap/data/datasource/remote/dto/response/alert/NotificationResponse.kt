package com.swapit.oopswap.data.datasource.remote.dto.response.alert

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val notificationsId: Long,
    val type: String,
    val title: String,
    val body: String,
    val relatedData: Long,
    val createdAt: String,
)
