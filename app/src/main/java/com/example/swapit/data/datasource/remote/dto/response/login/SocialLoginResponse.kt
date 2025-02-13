package com.example.swapit.data.datasource.remote.dto.response.login

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val key: String,
)
