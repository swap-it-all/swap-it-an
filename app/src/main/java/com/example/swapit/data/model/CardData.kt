package com.example.swapit.data.model

data class ShoppingCardData(
    val goodsId: Int,
    val imageUri: String,
    val category: String,
    val viewCount: String,
    val region: String,
    val time: String,
    val price: Int,
    val title: String,
    val onClick: () -> Unit = {},
)

data class AlertCardData(
    val message: String,
    val date: String,
    val icon: Int,
    val onClick: () -> Unit = {},
)

data class SwapCardData(
    val imageUri: String,
    val category: String,
    val viewCount: String,
    val region: String,
    val time: String,
    val price: Int,
    val title: String,
    val onClick: () -> Unit = {},
)

data class ChatCardData(
    val userImageUri: String,
    val userName: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadMessageCount: Int,
    val onClick: () -> Unit = {},
)
