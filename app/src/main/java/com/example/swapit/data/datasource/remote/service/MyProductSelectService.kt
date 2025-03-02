package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MyProductSelectService {
    @GET("/api/user/goods/my")
    suspend fun myProductSelectResponse(
    ): BaseResponse<List<ShoppingProductResponse>>
}
