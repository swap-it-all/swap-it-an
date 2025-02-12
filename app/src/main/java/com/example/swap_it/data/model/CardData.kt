package com.example.swap_it.data.model


data class ShoppingCardData(
    val imageUri: String,
    val category: String,
    val viewCount: String,
    val region: String,
    val time: String,
    val price: String,
    val title: String,
    val onClick: () -> Unit = {}
)

data class AlertCardData(
    val message: String,
    val date: String,
    val icon: Int,
    val onClick: () -> Unit = {}
)

data class SwapCardData(
    val imageUri: String,
    val category: String,
    val viewCount: String,
    val region: String,
    val time: String,
    val price: String,
    val title: String,
    val onClick: () -> Unit = {}
)