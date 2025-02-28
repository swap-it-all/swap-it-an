package com.example.swapit.data.mapper

import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailDataResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailImageResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailUserResponse
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailData
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailImage
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailUser

fun ShoppingDetailDataResponse.toDomain(): ShoppingDetailData {
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
        imageUri = this.imageUri.map { it.toDomain() },
        createdAt = this.createdAt,
    )
}

fun ShoppingDetailUserResponse.toDomain(): ShoppingDetailUser {
    return ShoppingDetailUser(
        userId = this.userId,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        userRating = this.userRating,
    )
}

fun ShoppingDetailImageResponse.toDomain(): ShoppingDetailImage {
    return ShoppingDetailImage(
        imagesId = this.imagesId,
        imageUrl = this.imageUrl
    )
}
