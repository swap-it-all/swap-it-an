package com.example.swap_it.domain.model

data class SocialLoginToken(
    val accessToken: String,
    val refreshToken: String,
    val key: String,
)
