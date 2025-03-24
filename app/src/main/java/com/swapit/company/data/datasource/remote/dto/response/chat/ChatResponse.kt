package com.swapit.company.data.datasource.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val chatsId: Long,
    val chatType: String,
    val content: String,
    val senderId: Long,
    val createdAt: String,
    val requesterGoods: ChatSwapProductResponse?,
)

@Serializable
data class ChatSwapProductResponse(
    val goodsId: Long,
    val title: String,
    val requesterNickname: String,
)

@Serializable
data class ChatListResponse(
    val chatList: List<ChatResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long?,
    val size: Int,
)
