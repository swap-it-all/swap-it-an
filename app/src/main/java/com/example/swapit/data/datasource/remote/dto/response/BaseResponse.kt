package com.example.swapit.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success: Boolean,
    val message: String,
    val results: T,
)
