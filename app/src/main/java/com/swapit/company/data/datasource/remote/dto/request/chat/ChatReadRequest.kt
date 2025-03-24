package com.swapit.company.data.datasource.remote.dto.request.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatReadRequest(
    val lastReadChatId: Long,
)
