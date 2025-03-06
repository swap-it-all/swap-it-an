package com.example.swapit.domain.model.product

import com.example.swapit.data.datasource.remote.dto.response.product.ProductResponse

data class ProductResults(
    val goodsList: List<ProductResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long?,
    val count: Long,
)
