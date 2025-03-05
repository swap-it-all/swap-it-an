package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.service.SwapRequestService

class RemoteSwapRequestDataSource(private val swapRequestService: SwapRequestService) {
    suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long> =
        swapRequestService.swapRequest(
            swapRequest
        )
}