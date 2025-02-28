package com.example.swapit.data.datasource.remote.dto.response.shopping.detail

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingDetailUserResponse(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val userRating: Double,
)
