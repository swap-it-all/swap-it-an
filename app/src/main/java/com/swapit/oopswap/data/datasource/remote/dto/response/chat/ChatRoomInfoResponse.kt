package com.swapit.oopswap.data.datasource.remote.dto.response.chat

import com.swapit.oopswap.data.datasource.remote.dto.response.product.detail.ProductDetailTradeResponse
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomInfoResponse(
    val goodsId: Long,
    val title: String,
    val category: String,
    val price: Long,
    val imageUrl: String,
    val usersId: Long,
    val nickname: String,
    val trade: ProductDetailTradeResponse?,
)
