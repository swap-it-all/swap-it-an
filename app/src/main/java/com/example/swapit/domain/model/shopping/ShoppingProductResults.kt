package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingCardProduct
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingCardProductsResults

data class ShoppingProductResults(
    val goodsList: List<ShoppingCardProduct>,
    val hasNext: Boolean,
    val lastCursorId: Int,
    val count: Int,
)

fun ShoppingCardProductsResults.toDomainModel(): ShoppingProductResults {
    return ShoppingProductResults(
        goodsList = this.goodsList,
        hasNext = this.hasNext,
        lastCursorId = this.lastCursorId,
        count = this.size,
    )
}
