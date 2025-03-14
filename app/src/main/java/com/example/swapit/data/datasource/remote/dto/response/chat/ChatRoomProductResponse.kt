package com.example.swapit.data.datasource.remote.dto.response.chat

import kotlinx.serialization.Serializable


@Serializable
data class ChatRoomProductResponse(
    val nickname: String,
    val goodsId: Long,
    val title: String,
    val category: String,
    val price: Long,
    val imageUrl: String,
)
