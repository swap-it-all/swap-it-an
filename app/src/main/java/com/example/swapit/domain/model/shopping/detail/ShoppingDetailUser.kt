package com.example.swapit.domain.model.shopping.detail

data class ShoppingDetailUser(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val userRating: Double,
)