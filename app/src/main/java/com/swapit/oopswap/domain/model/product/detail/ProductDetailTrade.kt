package com.swapit.oopswap.domain.model.product.detail

data class ProductDetailTrade(
    val tradesId: Long,
    val isRequester: Boolean,
    val status: String,
    val relatedGoodsId: Long,
)
