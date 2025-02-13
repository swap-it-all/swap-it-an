package com.example.swapit.domain.model

data class SocialLoginToken(
    val accessToken: String,
    val refreshToken: String,
    val key: String,
)
