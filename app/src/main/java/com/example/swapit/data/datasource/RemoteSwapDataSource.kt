package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapProductsResultResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.SentSwapResponse
import com.example.swapit.data.datasource.remote.service.SwapService

class RemoteSwapDataSource(private val swapService: SwapService) {
    suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long> =
        swapService.swapRequest(
            swapRequest,
        )

    suspend fun receivedSwap(): BaseResponse<ReceivedSwapResponse> =
        swapService.receivedSwap()

    suspend fun receivedSwapProductsResult(goodsId: Long): BaseResponse<ReceivedSwapProductsResultResponse> =
        swapService.receivedSwapProductsResult(goodsId)

    suspend fun sentSwap(): BaseResponse<SentSwapResponse> =
        swapService.sentSwap()
}
