package com.example.swapit.data.datasource.remote.dto.response.product.detail.slelect

import kotlinx.serialization.Serializable

@Serializable
data class ProductSelectResponse(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val goodTradeStatus: String,
    val imageUrl: String?,
    val placeName: String,
    val viewCount: Long,
    val createdAt: String,
)
