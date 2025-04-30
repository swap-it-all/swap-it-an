package com.swapit.oopswap.domain.model.alert

data class Alert(
    val notificationsId: Long,
    val type: String,
    val title: String,
    val body: String,
    val relatedData: Long?,
    val createdAt: String,
)
