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
    suspend fun swapRequest(swapRequest: SwapRequest): Result<BaseResponse<Long>>

    suspend fun swapCancel(tradesId: Long): Result<BaseResponse<Unit>>

    suspend fun swapAccept(tradesId: Long): Result<BaseResponse<Unit>>

    suspend fun swapReject(tradesId: Long): Result<BaseResponse<Unit>>

    suspend fun swapComplete(tradesId: Long): Result<BaseResponse<Unit>>

    suspend fun receivedSwap(): Result<List<ReceivedSwap>>

    suspend fun receivedSwapProductsResult(goodsId: Long): Result<ReceivedSwapProductsResult>

    suspend fun sentSwap(): Result<List<SentSwap>>

    companion object {
        private var instance: SwapRepository? = null

        fun instance(onLogout: () -> Unit = {}): SwapRepository {
            if (instance == null) {
                instance =
                    DefaultSwapRepository(
                        remoteSource = RemoteSwapDataSource(ServiceModule.swapRequestService),
                        onLogout = onLogout
                    )
            }
            return instance!!
        }
    }
}
