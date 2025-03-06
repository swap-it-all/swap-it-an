package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.product.ProductResponse

data class ShoppingProductResults(
    val goodsList: List<ProductResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long?,
    val count: Long,
)
