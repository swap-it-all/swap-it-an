package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductsResultsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShoppingService {
    @GET("api/all/goods")
    suspend fun shoppingProductsResponse(
        @Query("cursorId") cursorId: Long?,
        @Query("createdAt") createdAt: String?,
        @Query("cursorValue") cursorValue: Long?,
        @Query("sortBy") sortBy: String?,
        @Query("keyword") keyword: String?,
        @Query("categoryIds") categoryIds: List<Int>?,
    ): BaseResponse<ShoppingProductsResultsResponse>
}
