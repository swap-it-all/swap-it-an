package com.example.swapit.domain.model.shopping

data class ShoppingProduct(
    val goodsId: Int,
    val title: String,
    val price: Int,
    val category: String,
    val imageUrl: String?,
    val placeName: String?,
    val viewCount: Int,
    val createdAt: String
)