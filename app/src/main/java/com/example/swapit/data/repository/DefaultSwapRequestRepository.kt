package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteSwapRequestDataSource
import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.domain.repository.SwapRequestRepository

class DefaultSwapRequestRepository(
    private val remoteSource: RemoteSwapRequestDataSource,
) :SwapRequestRepository{
    override suspend fun swapRequest(
        swapRequest: SwapRequest
    ): BaseResponse<Unit> {
        return remoteSource.swapRequest(swapRequest)
    }

}