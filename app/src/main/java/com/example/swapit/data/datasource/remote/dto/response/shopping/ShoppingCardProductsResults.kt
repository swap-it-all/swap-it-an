package com.example.swapit.data.datasource.remote.dto.response.shopping

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingCardProductsResults(
    val goodsList: List<ShoppingCardProduct>,
    val hasNext: Boolean,
    val lastCursorId: Int,
    val size: Int,
)
