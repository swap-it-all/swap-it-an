package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShoppingService {
    @GET("api/all/goods")
    suspend fun getShoppingData(): GoodsListResponse
}
