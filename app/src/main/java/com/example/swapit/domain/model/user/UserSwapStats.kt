package com.example.swapit.domain.model.user

data class UserSwapStats(
    val totalGoodsCount: Long,
    val completedSwapCount: Long,
    val ratingAverage: Double,
)
