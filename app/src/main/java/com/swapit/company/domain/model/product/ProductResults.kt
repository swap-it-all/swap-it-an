package com.swapit.company.domain.model.product

import com.swapit.company.data.datasource.remote.dto.response.product.ProductResponse

data class ProductResults(
    val goodsList: List<ProductResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long?,
    val count: Long,
)
