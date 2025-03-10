package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapProductsResultResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapResultResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.SentSwapResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.SentSwapResultResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SwapService {
    @POST("api/user/swap/request")
    suspend fun swapRequest(
        @Body request: SwapRequest,
    ): BaseResponse<Long>

    @GET("api/user/swap/my-goods")
    suspend fun receivedSwap(): BaseResponse<ReceivedSwapResultResponse>

    @GET("api/user/swap/my-goods/{goodsId}/requests")
    suspend fun receivedSwapProductsResult(
        @Path("goodsId") goodsId: Long,
    ): BaseResponse<ReceivedSwapProductsResultResponse>

    @GET("api/user/swap/my-requests")
    suspend fun sentSwap(): BaseResponse<SentSwapResultResponse>
}
