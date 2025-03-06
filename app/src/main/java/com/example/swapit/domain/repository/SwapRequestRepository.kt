package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteSwapRequestDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.repository.DefaultSwapRequestRepository

interface SwapRequestRepository {
    suspend fun swapRequest(swapRequest: SwapRequest): BaseResponse<Long>

    companion object {
        private var instance: SwapRequestRepository? = null

        fun instance(): SwapRequestRepository {
            if (instance == null) {
                instance =
                    DefaultSwapRequestRepository(
                        remoteSource = RemoteSwapRequestDataSource(ServiceModule.swapRequestService),
                    )
            }
            return instance!!
        }
    }
}
