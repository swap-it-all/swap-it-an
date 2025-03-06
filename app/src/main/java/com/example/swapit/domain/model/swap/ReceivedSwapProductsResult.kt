package com.example.swapit.domain.model.swap

import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapProductsResponse

data class ReceivedSwapProductsResult(
    val myGoodsTitle: String,
    val goodsList: List<ReceivedSwapProductsResponse>,
)
