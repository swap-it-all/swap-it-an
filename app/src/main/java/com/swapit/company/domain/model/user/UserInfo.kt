package com.swapit.company.domain.model.user

data class UserInfo(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String,
    val email: String,
    val swapStats: UserSwapStats,
    val reviews: List<Review>,
)
