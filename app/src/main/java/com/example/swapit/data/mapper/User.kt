package com.example.swapit.data.mapper

import com.example.swapit.data.datasource.remote.dto.response.user.ReviewResponse
import com.example.swapit.data.datasource.remote.dto.response.user.UserResponse
import com.example.swapit.domain.model.user.Review
import com.example.swapit.domain.model.user.UserInfo
import com.example.swapit.domain.model.user.UserSwapStats

fun UserResponse.toDomain(): UserInfo {
    return UserInfo(
        id = this.id,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        email = this.email,
        swapStats =
            UserSwapStats(
                totalGoodsCount = this.totalGoodsCount,
                completedSwapCount = this.completedSwapCount,
                ratingAverage = this.ratingAverage,
            ),
        reviews = this.reviews.map { it.toDomain() },
    )
}

fun ReviewResponse.toDomain(): Review {
    return Review(
        userId = this.userId,
        profileImageUrl = this.profileImageUrl,
        nickName = this.nickName,
        rating = this.rating,
        content = this.content,
        createdAt = this.createdAt,
    )
}
