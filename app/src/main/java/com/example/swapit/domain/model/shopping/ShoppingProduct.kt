package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.shopping.Goods

data class ShoppingProduct(
    val goodsId: Int,
    val title: String,
    val price: Int,
    val category: String,
    val imageUrl: String?,
    val placeName: String?,
    val viewCount: Int,
    val createdAt: String,
)

fun Goods.toDomainModel(): ShoppingProduct {
    return ShoppingProduct(
        goodsId = this.id,
        title = this.title,
        price = this.price,
        category = this.category,
        imageUrl = this.imageUrl,
        placeName = this.placeName,
        viewCount = this.viewCount,
        createdAt = this.createdAt,
    )
}
