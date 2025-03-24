package com.swapit.company.data.mapper

import com.swapit.company.data.datasource.remote.dto.response.user.ReviewResponse
import com.swapit.company.data.datasource.remote.dto.response.user.UserResponse
import com.swapit.company.domain.model.user.Review
import com.swapit.company.domain.model.user.UserInfo
import com.swapit.company.domain.model.user.UserSwapStats

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