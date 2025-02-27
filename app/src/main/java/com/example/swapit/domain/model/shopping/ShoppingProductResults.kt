package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingCardProduct

data class ShoppingProductResults(
    val goodsList: List<ShoppingCardProduct>,
    val hasNext: Boolean,
    val lastCursorId: Int,
    val count: Int,
)
