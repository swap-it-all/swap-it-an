package com.example.swapit.data.mapper

import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailImageResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailUserResponse
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailData
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailImage
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailUser

fun ProductDetailResponse.toDomain(): ShoppingDetailData {
    return ShoppingDetailData(
        goodsId = this.goodsId,
        user = this.user.toDomain(),
        category = this.category,
        title = this.title,
        price = this.price,
        quality = this.quality,
        content = this.content,
        goodsTradeStatus = this.goodsTradeStatus,
        placeName = this.placeName,
        viewCount = this.viewCount,
        imageUri = this.images.map { it.toDomain() },
        createdAt = this.createdAt,
    )
}

fun ProductDetailUserResponse.toDomain(): ShoppingDetailUser {
    return ShoppingDetailUser(
        userId = this.userId,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        userRating = this.userRating,
    )
}

fun ProductDetailImageResponse.toDomain(): ShoppingDetailImage {
    return ShoppingDetailImage(
        imagesId = this.imagesId,
        imageUrl = this.imageUrl,
    )
}
