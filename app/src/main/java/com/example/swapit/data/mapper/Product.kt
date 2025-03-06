package com.example.swapit.data.mapper

import com.example.swapit.data.datasource.remote.dto.response.product.ProductResponse
import com.example.swapit.data.datasource.remote.dto.response.product.ProductResultResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailImageResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailUserResponse
import com.example.swapit.domain.model.product.Product
import com.example.swapit.domain.model.product.ProductResults
import com.example.swapit.domain.model.product.detail.ProductDetail
import com.example.swapit.domain.model.product.detail.ProductDetailImage
import com.example.swapit.domain.model.product.detail.ProductDetailUser

fun ProductResponse.toDomain(): Product {
    return Product(
        goodsId = this.goodsId,
        title = this.title,
        price = this.price,
        category = this.category,
        imageUrl = this.imageUrl,
        placeName = this.placeName,
        viewCount = this.viewCount,
        createdAt = this.createdAt,
    )
}

fun ProductResultResponse.toDomain(): ProductResults {
    return ProductResults(
        goodsList = this.goodsList,
        hasNext = this.hasNext,
        lastCursorId = this.lastCursorId,
        count = this.size,
    )
}

fun ProductDetailResponse.toDomain(): ProductDetail {
    return ProductDetail(
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

fun ProductDetailUserResponse.toDomain(): ProductDetailUser {
    return ProductDetailUser(
        userId = this.userId,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        userRating = this.userRating,
    )
}

fun ProductDetailImageResponse.toDomain(): ProductDetailImage {
    return ProductDetailImage(
        imagesId = this.imagesId,
        imageUrl = this.imageUrl,
    )
}
