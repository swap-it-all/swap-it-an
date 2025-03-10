package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteSwapDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.repository.DefaultSwapRepository
import com.example.swapit.domain.model.swap.ReceivedSwap
import com.example.swapit.domain.model.swap.ReceivedSwapProductsResult
import com.example.swapit.domain.model.swap.SentSwap

interface SwapRepository {
    suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long>

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
