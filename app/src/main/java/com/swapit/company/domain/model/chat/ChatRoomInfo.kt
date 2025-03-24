package com.swapit.company.domain.model.chat

data class ChatRoomInfo(
    val goodsId: Long,
    val title: String,
    val category: String,
    val price: Long,
    val imageUrl: String,
    val usersId: Long,
    val nickname: String,
)
