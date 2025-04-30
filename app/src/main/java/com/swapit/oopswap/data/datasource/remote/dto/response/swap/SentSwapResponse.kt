package com.swapit.oopswap.data.datasource.remote.dto.response.swap

import kotlinx.serialization.Serializable

@Serializable
data class SentSwapResponse(
    val tradesId: Long,
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val placeName: String,
    val myGoodsPhotoUrl: String?,
    val targetGoodsPhotoUrl: String?,
    val targetGoodsViewCount: Long,
    val createdAt: String,
    val isInProgress: Boolean,
)

@Serializable
data class SentSwapResultResponse(
    val goodsList: List<SentSwapResponse>,
)
