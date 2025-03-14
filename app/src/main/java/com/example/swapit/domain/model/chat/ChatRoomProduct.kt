package com.example.swapit.domain.model.chat

data class ChatRoomProduct(
    val nickname: String,
    val goodsId: Long,
    val title: String,
    val category: String,
    val price: Long,
    val imageUrl: String,
)
