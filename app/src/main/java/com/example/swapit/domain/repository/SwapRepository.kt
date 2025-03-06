package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteSwapDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.repository.DefaultSwapRepository

interface SwapRepository {
    suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long>

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
