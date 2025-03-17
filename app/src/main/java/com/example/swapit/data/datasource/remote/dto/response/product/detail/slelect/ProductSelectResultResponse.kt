package com.example.swapit.data.datasource.remote.dto.response.product.detail.slelect

import kotlinx.serialization.Serializable

@Serializable
data class ProductSelectResultResponse(
    val data: List<ProductSelectResponse>,
)
