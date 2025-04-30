package com.swapit.oopswap.domain.repository

import com.swapit.oopswap.data.datasource.RemoteSwapDataSource
import com.swapit.oopswap.data.datasource.remote.ServiceModule
import com.swapit.oopswap.data.datasource.remote.dto.request.swap.SwapRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.repository.DefaultSwapRepository
import com.swapit.oopswap.domain.model.swap.ReceivedSwap
import com.swapit.oopswap.domain.model.swap.ReceivedSwapProductsResult
import com.swapit.oopswap.domain.model.swap.SentSwap

interface SwapRepository {
    suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long>

    suspend fun swapCancel(tradesId: Long): BaseResponse<Unit>

    suspend fun swapAccept(tradesId: Long): BaseResponse<Unit>

    suspend fun swapReject(tradesId: Long): BaseResponse<Unit>

    suspend fun swapComplete(tradesId: Long): BaseResponse<Unit>

    suspend fun receivedSwap(): List<ReceivedSwap>

    suspend fun receivedSwapProductsResult(goodsId: Long): ReceivedSwapProductsResult

    suspend fun sentSwap(): List<SentSwap>

    companion object {
        private var instance: SwapRepository? = null

        fun instance(): SwapRepository {
            if (instance == null) {
                instance =
                    DefaultSwapRepository(
                        remoteSource = RemoteSwapDataSource(ServiceModule.swapRequestService),
                    )
            }
            return instance!!
        }
    }
}
