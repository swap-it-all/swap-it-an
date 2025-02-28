package com.example.swapit.domain.model.shopping.detail

data class ShoppingDetailData(
    val goodsId: Int,
    val user: ShoppingDetailUser,
    val category: String,
    val title: String,
    val price: Int,
    val quality: String,
    val content: String,
    val goodsTradeStatus: String,
    val placeName: String,
    val viewCount: Int,
    val imageUri: List<ShoppingDetailImage>,
    val createdAt: String,
)
