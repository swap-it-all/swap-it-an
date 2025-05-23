package com.swapit.oopswap.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: Int,
    val errorCode: String,
    val message: String,
)
