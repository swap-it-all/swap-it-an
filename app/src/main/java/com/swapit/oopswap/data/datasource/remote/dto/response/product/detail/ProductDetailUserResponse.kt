package com.swapit.oopswap.data.datasource.remote.dto.response.product.detail

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailUserResponse(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val userRating: Double,
)
