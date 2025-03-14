package com.example.swapit.data.datasource.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomResponse(
    val chatroomsId: Long,
    val usersId: Long,
    val profileImageUrl: String,
    val nickname: String,
    val recentChat: String,
    val recentChatTime: String,
)

@Serializable
data class ChatRoomListResponse(
    val chatRoomList: List<ChatRoomResponse>,
)

