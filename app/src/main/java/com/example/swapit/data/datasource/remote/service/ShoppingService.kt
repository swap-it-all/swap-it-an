package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResponse
import retrofit2.http.GET

interface ShoppingService {
    @GET("api/all/goods")
    suspend fun getGoodsListResponse(): GoodsListResponse
}
