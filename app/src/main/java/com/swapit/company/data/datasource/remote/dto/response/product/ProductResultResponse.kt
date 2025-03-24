package com.swapit.company.data.datasource.remote.dto.response.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductResultResponse(
    val goodsList: List<ProductResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long?,
    val size: Long,
)
