package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ShoppingDetailService {
    @GET("/api/all/goods/{goodsId}")
    suspend fun shoppingDetailResponse(
        @Path("goodsId") goodsId: String,
    ): BaseResponse<ProductDetailResponse>
}
