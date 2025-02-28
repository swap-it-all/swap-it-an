package com.example.swapit.data.datasource.remote.dto.response.shopping

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingProductResponse(
    val goodsId: Int,
    val title: String,
    val price: Int,
    val category: String,
    val imageUrl: String?,
    val placeName: String,
    val viewCount: Int,
    val createdAt: String,
)
