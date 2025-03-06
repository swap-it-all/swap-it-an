package com.example.swapit.data.datasource.remote.dto.response.swap

import kotlinx.serialization.Serializable

@Serializable
data class ReceivedSwapProductsResponse(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val photoUrl: String?,
    val placeName: String,
    val createdAt: String,
)

