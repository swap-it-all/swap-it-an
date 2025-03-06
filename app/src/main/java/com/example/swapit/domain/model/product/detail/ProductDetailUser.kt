package com.example.swapit.domain.model.product.detail

data class ProductDetailUser(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val userRating: Double,
)
