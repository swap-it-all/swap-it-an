package com.swapit.company.domain.model.product.detail

data class ProductDetail(
    val goodsId: Long,
    val user: ProductDetailUser,
    val category: String,
    val title: String,
    val price: Long,
    val quality: String,
    val content: String,
    val goodsTradeStatus: String,
    val placeName: String,
    val viewCount: Long,
    val imageUri: List<ProductDetailImage>,
    val trade: ProductDetailTrade?,
    val createdAt: String,
)
