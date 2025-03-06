package com.example.swapit.data.datasource.remote.dto.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequest(
    val nickname: String,
)
