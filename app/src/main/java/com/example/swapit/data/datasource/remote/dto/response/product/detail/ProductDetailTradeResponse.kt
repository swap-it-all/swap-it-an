package com.example.swapit.data.datasource.remote.dto.response.product.detail

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailTradeResponse(
    val tradesId: Long,
    val isRequester: Boolean,
    val status: String,
    val relatedGoodsId: Long,
)
