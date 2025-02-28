package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ShoppingDetailService {
    @GET("/api/all/goods/{goodsId}")
    suspend fun shoppingDetailResponse(
        @Path("goodsId") goodsId: String,
    ): BaseResponse<ShoppingDetailDataResponse>
}
