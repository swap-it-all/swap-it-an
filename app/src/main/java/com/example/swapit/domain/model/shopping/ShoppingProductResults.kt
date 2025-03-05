package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductResponse

data class ShoppingProductResults(
    val goodsList: List<ShoppingProductResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long,
    val count: Long,
)
