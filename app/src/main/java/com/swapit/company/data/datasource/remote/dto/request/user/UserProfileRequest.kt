package com.swapit.company.data.datasource.remote.dto.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequest(
    val nickname: String,
)
