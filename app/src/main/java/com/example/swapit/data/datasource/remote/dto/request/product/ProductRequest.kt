package com.example.swapit.data.datasource.remote.dto.request.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductRequest(
    val title: String,
    val price: Int,
    val quality: String,
    val category: Int,
    @SerialName("content")
    val description: String,
    val placeName: String,
)
