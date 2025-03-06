package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteSwapDataSource
import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.domain.repository.SwapRepository

class DefaultSwapRepository(
    private val remoteSource: RemoteSwapDataSource,
) : SwapRepository {
    override suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long> {
        return remoteSource.swapRequest(swapRequest)
    }
}
