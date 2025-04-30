package com.swapit.oopswap.data.datasource.remote.dto.request.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val chatType: String,
    val content: String,
    val goodsId: Long?,
)
