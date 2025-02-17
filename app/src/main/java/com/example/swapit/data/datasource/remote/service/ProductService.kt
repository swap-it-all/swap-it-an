package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.request.product.ProductRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProductService {
    @POST("api/user/goods/register")
    suspend fun postProduct(
        @Body product: ProductRequest,
    ): BaseResponse<Int>

    @Multipart
    @POST("api/user/{goodsId}/images")
    suspend fun postProductImages(
        @Path("goodsId") goodsId: Int,
        @Part images: List<MultipartBody.Part>,
    ): BaseResponse<Unit>
}
