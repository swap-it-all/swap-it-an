package com.example.swapit.domain.model.chat

data class ChatRoom(
    val chatroomsId: Long,
    val usersId: Long,
    val profileImageUrl: String,
    val nickname: String,
    val recentChat: String,
    val recentChatTime: String,
)