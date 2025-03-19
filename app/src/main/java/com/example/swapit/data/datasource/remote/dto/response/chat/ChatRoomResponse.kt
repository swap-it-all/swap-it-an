package com.example.swapit.data.datasource.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomResponse(
    val unReadChatCount: Long,
    val chatroomId: Long,
    val profileImageUrl: String,
    val nickname: String,
    val recentChat: String,
    val recentChatTime: String,
)

@Serializable
data class ChatRoomListResponse(
    val chatRoomList: List<ChatRoomResponse>,
)
