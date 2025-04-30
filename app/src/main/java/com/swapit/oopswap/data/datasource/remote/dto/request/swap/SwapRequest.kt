package com.swapit.oopswap.data.datasource.remote.dto.request.swap

import kotlinx.serialization.Serializable

@Serializable
data class SwapRequest(
    val requestedGoodsId: Long,
    val targetGoodsId: Long,
)
