package com.swapit.company.domain.model

data class LoginToken(
    val accessToken: String,
    val refreshToken: String,
    val key: String,
)
