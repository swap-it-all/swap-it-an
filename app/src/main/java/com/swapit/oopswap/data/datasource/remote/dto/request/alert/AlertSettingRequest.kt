package com.swapit.oopswap.data.datasource.remote.dto.request.alert

import kotlinx.serialization.Serializable

@Serializable
data class AlertSettingRequest(
    val notificationEnabled: Boolean,
)
