package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SwapRequestService {
    @POST("api/user/swap/request")
    suspend fun swapRequest(
        @Body request: SwapRequest
    ): BaseResponse<Long>
}