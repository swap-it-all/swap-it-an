package com.example.swapit.data.datasource.remote.dto.response.shopping.detail

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingDetailDataResponse(
    val goodsId: Long,
    val user: ShoppingDetailUserResponse,
    val category: String,
    val title: String,
    val price: Long,
    val quality: String,
    val content: String,
    val goodsTradeStatus: String,
    val placeName: String,
    val viewCount: Long,
    val images: List<ShoppingDetailImageResponse>,
    val createdAt: String,
)