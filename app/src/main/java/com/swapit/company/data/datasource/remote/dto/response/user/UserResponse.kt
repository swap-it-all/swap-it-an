package com.swapit.company.data.datasource.remote.dto.response.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("usersId") val id: Long,
    val nickname: String,
    val profileImageUrl: String,
    val email: String,
    val totalGoodsCount: Long,
    val completedSwapCount: Long,
    val ratingAverage: Double,
    val totalReviewCount: Long,
    val reviews: List<ReviewResponse>,
)
