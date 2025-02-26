package com.example.swapit.data.datasource.remote.dto.response.shopping

import kotlinx.serialization.Serializable

@Serializable
data class GoodsListResponse(
    val success: Boolean,
    val message: String,
    val results: GoodsListResults
)