package com.swapit.company.data.datasource.remote.dto.response.chat

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
)
