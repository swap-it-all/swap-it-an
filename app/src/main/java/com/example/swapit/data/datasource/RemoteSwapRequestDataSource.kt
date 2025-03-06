package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.service.SwapService

class RemoteSwapRequestDataSource(private val swapRequestService: SwapService) {
    suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long> =
        swapRequestService.swapRequest(
            swapRequest,
        )
}
