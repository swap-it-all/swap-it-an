package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.request.product.ProductRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.product.ProductResponse
import com.example.swapit.data.datasource.remote.dto.response.product.ProductResultResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @POST("api/user/goods/register")
    suspend fun postProduct(
        @Body product: ProductRequest,
    ): BaseResponse<Long>

    @Multipart
    @POST("api/user/goods/{goodsId}/images")
    suspend fun postProductImages(
        @Path("goodsId") goodsId: Long,
        @Part images: List<MultipartBody.Part>,
    ): BaseResponse<Unit>

    @GET("/api/user/goods/my")
    suspend fun myProductSelection(): BaseResponse<List<ProductResponse>>

    @GET("/api/all/goods/{goodsId}")
    suspend fun shoppingProductDetail(
        @Path("goodsId") goodsId: String,
    ): BaseResponse<ProductDetailResponse>

    @GET("api/all/goods")
    suspend fun shoppingProducts(
        @Query("cursorId") cursorId: Long?,
        @Query("createdAt") createdAt: String?,
        @Query("cursorValue") cursorValue: Long?,
        @Query("sortBy") sortBy: String?,
        @Query("keyword") keyword: String?,
        @Query("categoryIds") categoryIds: List<Int>?,
    ): BaseResponse<ProductResultResponse>
}
