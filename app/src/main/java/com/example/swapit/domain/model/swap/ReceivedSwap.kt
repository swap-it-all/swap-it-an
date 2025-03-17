package com.example.swapit.domain.model.swap

data class ReceivedSwap(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val photoUrl: String?,
    val placeName: String,
    val viewCount: Long,
    val requestCount: Long,
    val inProgressCount: Long,
    val createdAt: String,
)
