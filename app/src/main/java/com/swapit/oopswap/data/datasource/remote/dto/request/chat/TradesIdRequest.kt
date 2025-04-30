package com.swapit.oopswap.data.datasource.remote.dto.request.chat

import kotlinx.serialization.Serializable

@Serializable
data class TradesIdRequest(
    val tradesId: Long,
)
