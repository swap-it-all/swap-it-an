package com.swapit.oopswap.data.datasource.remote.dto.response.alert

import kotlinx.serialization.Serializable

@Serializable
data class AlertListResponse(
    val notifications: List<AlertResponse>,
)
