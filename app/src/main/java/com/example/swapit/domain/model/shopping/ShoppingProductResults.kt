package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.shopping.Goods

data class ShoppingProductResults(
    val goodsList: List<Goods>,
    val hasNext: Boolean,
    val lastCursorId: Int,
    val size: Int
)