package com.swapit.company.domain.model.alert

data class Alert(
    val notificationsId: Long,
    val type: String,
    val title: String,
    val body: String,
    val deeplink: String,
    val createdAt: String
)