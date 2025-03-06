package com.example.swapit.data.datasource.remote.dto.response.swap

import kotlinx.serialization.Serializable

@Serializable
data class SentSwapResponse(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val placeName: String,
    val myGoodsPhotoUrl: String?,
    val targetGoodsPhotoUrl: String?,
    val targetGoodsViewCount: Long,
    val createdAt: String,
)

