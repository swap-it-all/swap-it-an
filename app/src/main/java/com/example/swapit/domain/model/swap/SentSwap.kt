package com.example.swapit.domain.model.swap

data class SentSwap(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val placeName: String,
    val myGoodsPhotoUrl: String?,
    val targetGoodsPhotoUrl: String?,
    val targetGoodsViewCount: Long,
    val createdAt: String,
)