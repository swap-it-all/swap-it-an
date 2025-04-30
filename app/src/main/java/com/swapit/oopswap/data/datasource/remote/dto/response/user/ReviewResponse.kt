package com.swapit.oopswap.data.datasource.remote.dto.response.user

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    val userId: Long,
    val profileImageUrl: String,
    val nickName: String,
    val rating: Double,
    val content: String,
    val createdAt: String,
)
