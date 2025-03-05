package com.example.swapit.data.datasource.remote.dto.response.shopping

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingProductsResultsResponse(
    val goodsList: List<ShoppingProductResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long?,
    val size: Long,
)
