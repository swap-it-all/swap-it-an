package com.example.swapit.data.datasource.remote.dto.response.shopping.detail

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingDetailImageResponse(
    val imagesId: Long,
    val imageUrl: String,
)