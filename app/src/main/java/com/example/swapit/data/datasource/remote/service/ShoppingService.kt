package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductsResultsResponse
import retrofit2.http.GET

interface ShoppingService {
    @GET("api/all/goods")
    suspend fun shoppingProductsResponse(): BaseResponse<ShoppingProductsResultsResponse>
}
