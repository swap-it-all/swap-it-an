package com.swapit.oopswap.data.repository

import android.util.Log
import com.swapit.oopswap.data.datasource.RemoteSwapDataSource
import com.swapit.oopswap.data.datasource.remote.dto.request.swap.SwapRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.mapper.toDomain
import com.swapit.oopswap.domain.model.swap.ReceivedSwap
import com.swapit.oopswap.domain.model.swap.ReceivedSwapProductsResult
import com.swapit.oopswap.domain.model.swap.SentSwap
import com.swapit.oopswap.domain.repository.SwapRepository

class DefaultSwapRepository(
    private val remoteSource: RemoteSwapDataSource,
    private val onLogout: () -> Unit
) : SwapRepository {
    override suspend fun swapRequest(swapRequest: SwapRequest): Result<BaseResponse<Long>> =
        safeApiCall(onLogout) {
        remoteSource.swapRequest(swapRequest)
    }

    override suspend fun swapCancel(tradesId: Long): Result<BaseResponse<Unit>> =
        safeApiCall(onLogout) {
        remoteSource.swapCancel(tradesId)
    }

    override suspend fun swapAccept(tradesId: Long): Result<BaseResponse<Unit>> =
        safeApiCall(onLogout) {
        remoteSource.swapAccept(tradesId)
    }

    override suspend fun swapReject(tradesId: Long): Result<BaseResponse<Unit>> =
        safeApiCall(onLogout) {
        remoteSource.swapReject(tradesId)
    }

    override suspend fun swapComplete(tradesId: Long): Result<BaseResponse<Unit>> =
        safeApiCall(onLogout) {
        remoteSource.swapComplete(tradesId)
    }

    override suspend fun receivedSwap(): Result<List<ReceivedSwap>> =
        safeApiCall(onLogout) {
        if (remoteSource.receivedSwap().success) {
            Log.d("SwapRepository", "Received swap success")
        } else {
            Log.e("SwapRepository", "Received swap failed")
        }
        remoteSource.receivedSwap().results.goodsList.map { it.toDomain() }
    }

    override suspend fun receivedSwapProductsResult(goodsId: Long): Result<ReceivedSwapProductsResult> =
        safeApiCall(onLogout) {
        if (remoteSource.receivedSwapProductsResult(goodsId).success) {
            Log.d("SwapRepository", "Received swap products result success")
        } else {
            Log.e("SwapRepository", "Received swap products result failed")
        }
        remoteSource.receivedSwapProductsResult(goodsId).results.toDomain()
    }

    override suspend fun sentSwap(): Result<List<SentSwap>> =
        safeApiCall(onLogout) {
        if (remoteSource.sentSwap().success) {
            Log.d("SwapRepository", "Sent swap success")
        } else {
            Log.e("SwapRepository", "Sent swap failed")
        }
        remoteSource.sentSwap().results.goodsList.map { it.toDomain() }
    }
}
