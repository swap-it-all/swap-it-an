package com.swapit.oopswap.domain.model.swap

import com.swapit.oopswap.data.datasource.remote.dto.response.swap.ReceivedSwapProductsResponse

data class ReceivedSwapProductsResult(
    val myGoodsTitle: String,
    val goodsList: List<ReceivedSwapProductsResponse>,
)
