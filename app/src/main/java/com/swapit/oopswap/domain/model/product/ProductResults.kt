package com.swapit.oopswap.domain.model.product

import com.swapit.oopswap.data.datasource.remote.dto.response.product.ProductResponse

data class ProductResults(
    val goodsList: List<ProductResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long?,
    val count: Long,
)
