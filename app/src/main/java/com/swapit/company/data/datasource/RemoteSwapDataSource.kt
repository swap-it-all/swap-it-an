package com.swapit.company.data.datasource

import com.swapit.company.data.datasource.remote.dto.request.swap.SwapRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.swap.ReceivedSwapProductsResultResponse
import com.swapit.company.data.datasource.remote.dto.response.swap.ReceivedSwapResultResponse
import com.swapit.company.data.datasource.remote.dto.response.swap.SentSwapResultResponse
import com.swapit.company.data.datasource.remote.service.SwapService

class RemoteSwapDataSource(private val swapService: SwapService) {
    suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long> = swapService.swapRequest(swapRequest)

    suspend fun swapCancel(tradesId: Long): BaseResponse<Unit> = swapService.swapCancel(tradesId)

    suspend fun swapAccept(tradesId: Long): BaseResponse<Unit> = swapService.swapAccept(tradesId)

    suspend fun swapReject(tradesId: Long): BaseResponse<Unit> = swapService.swapReject(tradesId)

    suspend fun swapComplete(tradesId: Long): BaseResponse<Unit> = swapService.swapComplete(tradesId)

    suspend fun receivedSwap(): BaseResponse<ReceivedSwapResultResponse> = swapService.receivedSwap()

    suspend fun receivedSwapProductsResult(goodsId: Long): BaseResponse<ReceivedSwapProductsResultResponse> =
        swapService.receivedSwapProductsResult(goodsId)

    suspend fun sentSwap(): BaseResponse<SentSwapResultResponse> = swapService.sentSwap()
}
