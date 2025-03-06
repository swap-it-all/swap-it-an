package com.example.swapit.data.datasource.remote.dto.response.product.detail

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailImageResponse(
    val imagesId: Long,
    val imageUrl: String,
)
