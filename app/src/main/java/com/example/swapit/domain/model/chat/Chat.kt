package com.example.swapit.domain.model.chat

import com.example.swapit.data.datasource.remote.dto.response.chat.ChatResponse
import com.example.swapit.data.datasource.remote.dto.response.chat.ChatSwapProductResponse

data class Chat(
    val chatsId: Long,
    val chatType: String,
    val content: String,
    val senderId: Long,
    val createdAt: String,
    val requesterGoods: ChatSwapProductResponse?,
)

data class ChatSwapProduct(
    val goodsId: Long,
    val title: String,
    val requesterNickname: String,
)

data class ChatList(
    val chatList: List<ChatResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long?,
    val size: Int,
)
