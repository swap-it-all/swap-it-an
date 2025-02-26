package com.example.swapit.data.model

import java.time.LocalDateTime

data class ChatRoomData(
    val imageUri: String,
    val category: String,
    val price: Int,
    val title: String,
    val goodsId: Int,
)

data class Chat(
    val chatsId: Int,
    val chatType: String,
    val content: String,
    val senderId: Long,
    val createdAt: LocalDateTime,
)
