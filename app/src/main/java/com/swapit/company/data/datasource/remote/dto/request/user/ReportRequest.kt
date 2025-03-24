package com.swapit.company.data.datasource.remote.dto.request.user

import kotlinx.serialization.Serializable

@Serializable
data class ReportRequest(
    val reportedId: Long,
    val reportType: String,
    val content: String,
)

