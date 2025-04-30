package com.swapit.oopswap.domain.model.product

data class Product(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val goodsTradeStatus: String,
    val imageUrl: String?,
    val placeName: String,
    val viewCount: Long,
    val createdAt: String,
)
