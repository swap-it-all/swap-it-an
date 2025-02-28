package com.example.swapit.domain.model.shopping.detail

data class ShoppingDetailData(
    val goodsId: Long,
    val user: ShoppingDetailUser,
    val category: String,
    val title: String,
    val price: Long,
    val quality: String,
    val content: String,
    val goodsTradeStatus: String,
    val placeName: String,
    val viewCount: Long,
    val imageUri: List<ShoppingDetailImage>,
    val createdAt: String,
)
