package com.swapit.oopswap.data.datasource.remote.dto.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequest(
    val nickname: String,
)
