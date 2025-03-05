package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SwapRequestService {
    @POST("api/user/swap/request")
    suspend fun swapRequest(
        @Body request: SwapRequest,
    ): BaseResponse<Long>
}
