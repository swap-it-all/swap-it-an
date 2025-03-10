package com.example.swapit.domain.model.product.detail.select

data class ProductSelect(
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