package com.example.swapit.data.datasource.remote.dto.response.swap

import kotlinx.serialization.Serializable

@Serializable
data class ReceivedSwapProductsResultResponse(
    val myGoodsTitle: String,
    val goodsList: List<ReceivedSwapProductsResponse>,
)

