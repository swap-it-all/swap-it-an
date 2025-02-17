package com.example.swapit.domain.model

data class LoginToken(
    val accessToken: String,
    val refreshToken: String,
    val key: String,
)
