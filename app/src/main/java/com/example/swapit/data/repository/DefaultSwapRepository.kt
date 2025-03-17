package com.example.swapit.data.repository

import android.util.Log
import com.example.swapit.data.datasource.RemoteSwapDataSource
import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.swap.ReceivedSwap
import com.example.swapit.domain.model.swap.ReceivedSwapProductsResult
import com.example.swapit.domain.model.swap.SentSwap
import com.example.swapit.domain.repository.SwapRepository

class DefaultSwapRepository(
    private val remoteSource: RemoteSwapDataSource,
) : SwapRepository {
    override suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long> {
        return remoteSource.swapRequest(swapRequest)
    }

    override suspend fun swapCancel(tradesId: Long): BaseResponse<Unit> {
        return remoteSource.swapCancel(tradesId)
    }

    override suspend fun swapAccept(tradesId: Long): BaseResponse<Unit> {
        return remoteSource.swapAccept(tradesId)
    }

    override suspend fun swapReject(tradesId: Long): BaseResponse<Unit> {
        return remoteSource.swapReject(tradesId)
    }

    override suspend fun swapComplete(tradesId: Long): BaseResponse<Unit> {
        return remoteSource.swapComplete(tradesId)
    }

    override suspend fun receivedSwap(): List<ReceivedSwap> {
        if (remoteSource.receivedSwap().success) {
            Log.d("SwapRepository", "Received swap success")
        } else {
            Log.e("SwapRepository", "Received swap failed")
        }
        return remoteSource.receivedSwap().results.goodsList.map { it.toDomain() }
    }

    override suspend fun receivedSwapProductsResult(goodsId: Long): ReceivedSwapProductsResult {
        if (remoteSource.receivedSwapProductsResult(goodsId).success) {
            Log.d("SwapRepository", "Received swap products result success")
        } else {
            Log.e("SwapRepository", "Received swap products result failed")
        }
        return remoteSource.receivedSwapProductsResult(goodsId).results.toDomain()
    }

    override suspend fun sentSwap(): List<SentSwap> {
        if (remoteSource.sentSwap().success) {
            Log.d("SwapRepository", "Sent swap success")
        } else {
            Log.e("SwapRepository", "Sent swap failed")
        }
        return remoteSource.sentSwap().results.goodsList.map { it.toDomain() }
    }
}
