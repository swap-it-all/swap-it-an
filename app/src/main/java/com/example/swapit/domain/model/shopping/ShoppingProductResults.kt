package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.shopping.Goods
import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResults

data class ShoppingProductResults(
    val goodsList: List<Goods>,
    val hasNext: Boolean,
    val lastCursorId: Int,
    val count: Int
)

fun GoodsListResults.toDomainModel(): ShoppingProductResults {
    return ShoppingProductResults(
        goodsList = this.goodsList,
        hasNext = this.hasNext,
        lastCursorId = this.lastCursorId,
        count = this.size
    )
}
