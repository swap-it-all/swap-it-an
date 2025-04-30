package com.swapit.oopswap.domain.model.swap

data class ReceivedSwapProduct(
    val goodsId: Long,
    val title: String,
    val price: Long,
    val category: String,
    val photoUrl: String?,
    val placeName: String,
    val createdAt: String,
    val isInProgress: Boolean,
)
