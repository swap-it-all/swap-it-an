package com.swapit.company.data.datasource.remote.dto.response.alert

import kotlinx.serialization.Serializable

@Serializable
data class AlertListResponse(
    val notifications: List<AlertResponse>
)