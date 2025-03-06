package com.example.swapit.data.datasource.remote.dto.response.product.detail

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailResponse(
    val goodsId: Long,
    val user: ProductDetailUserResponse,
    val category: String,
    val title: String,
    val price: Long,
    val quality: String,
    val content: String,
    val goodsTradeStatus: String,
    val placeName: String,
    val viewCount: Long,
    val images: List<ProductDetailImageResponse>,
    val createdAt: String,
)
