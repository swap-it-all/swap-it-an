package com.swapit.oopswap.domain.model.chat

data class ChatRoom(
    val unReadChatCount: Long,
    val chatroomId: Long,
    val profileImageUrl: String,
    val nickname: String,
    val recentChat: String,
    val recentChatTime: String,
)
