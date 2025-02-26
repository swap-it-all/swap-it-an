package com.example.swapit.data.datasource.remote.dto.response.shopping

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoodsListResults(
    @SerialName("goodsList") val goods: List<Goods>,
    val hasNext: Boolean,
    val lastCursorId: Int,
    val size: Int
)
