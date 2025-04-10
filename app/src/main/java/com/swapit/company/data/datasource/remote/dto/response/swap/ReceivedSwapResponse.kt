package com.swapit.company.data.datasource.remote.dto.response.swap

import kotlinx.serialization.Serializable

@Serializable
data class ReceivedSwapResponse(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val photoUrl: String?,
    val placeName: String,
    val viewCount: Long,
    val requestCount: Long,
    val createdAt: String,
    val isInProgress: Boolean,
)

@Serializable
data class ReceivedSwapResultResponse(
    val goodsList: List<ReceivedSwapResponse>,
)
