package com.example.swapit.domain.model.user

data class Review(
    val userId: Long,
    val profileImageUrl: String,
    val nickName: String,
    val rating: Double,
    val content: String,
    val createdAt: String,
)
