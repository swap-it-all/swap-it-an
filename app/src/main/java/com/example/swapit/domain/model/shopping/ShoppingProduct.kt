package com.example.swapit.domain.model.shopping

data class ShoppingProduct(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val imageUrl: String?,
    val placeName: String,
    val viewCount: Long,
    val createdAt: String,
)
