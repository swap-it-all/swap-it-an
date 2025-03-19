package com.example.swapit.data.model

data class AlertCardData(
    val message: String,
    val date: String,
    val icon: Int,
    val onClick: () -> Unit = {},
)
