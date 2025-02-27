package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResults

data class ShoppingProductResponse(
    val success: Boolean,
    val message: String,
    val results: GoodsListResults
)

fun GoodsListResponse.toDomainModel(): ShoppingProductResponse {
    return ShoppingProductResponse(
        success = this.success,
        message = this.message,
        results = this.results,
    )
}

