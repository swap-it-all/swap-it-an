package com.example.swapit.domain.model.shopping

import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResults

data class ShoppingProductResponse(
    val success: Boolean,
    val message: String,
    val results: GoodsListResults
)