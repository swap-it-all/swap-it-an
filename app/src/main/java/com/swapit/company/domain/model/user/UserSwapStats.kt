package com.swapit.company.domain.model.user

data class UserSwapStats(
    val totalGoodsCount: Long,
    val completedSwapCount: Long,
    val ratingAverage: Double,
)
