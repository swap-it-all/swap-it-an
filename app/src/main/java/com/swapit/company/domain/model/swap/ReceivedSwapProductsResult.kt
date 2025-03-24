package com.swapit.company.domain.model.swap

import com.swapit.company.data.datasource.remote.dto.response.swap.ReceivedSwapProductsResponse

data class ReceivedSwapProductsResult(
    val myGoodsTitle: String,
    val goodsList: List<ReceivedSwapProductsResponse>,
)
