package com.swapit.oopswap.data.datasource.remote.dto.request.login

import kotlinx.serialization.Serializable

@Serializable
data class WithDrawRequest(
    val reason: String,
)
