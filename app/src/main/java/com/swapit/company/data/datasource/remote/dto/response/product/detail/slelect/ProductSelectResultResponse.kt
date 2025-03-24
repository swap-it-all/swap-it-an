package com.swapit.company.data.datasource.remote.dto.response.product.detail.slelect

import kotlinx.serialization.Serializable

@Serializable
data class ProductSelectResultResponse(
    val data: List<ProductSelectResponse>,
)
