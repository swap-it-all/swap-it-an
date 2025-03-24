package com.swapit.company.data.datasource.remote.dto.request.chat

import kotlinx.serialization.Serializable

@Serializable
data class GoodsIdRequest(
    val goodsId: Long,
)
