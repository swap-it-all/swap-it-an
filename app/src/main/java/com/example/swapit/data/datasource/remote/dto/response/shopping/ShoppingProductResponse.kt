package com.example.swapit.data.datasource.remote.dto.response.shopping

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingProductResponse(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val imageUrl: String?,
    val placeName: String,
    val viewCount: Long,
    val createdAt: String,
)
