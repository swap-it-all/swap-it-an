package com.example.swap_it.data.model

data class ShoppingDetailData(
    val imageUri: List<String>,
    val category: String,
    val quality: String,
    val time: String,
    val title: String,
    val viewCount: String,
    val price: Int,
    val userImageUri: String,
    val userName: String,
    val rate: Float,
    val region: String,
    val content: String,
)