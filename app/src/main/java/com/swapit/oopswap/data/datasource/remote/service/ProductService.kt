package com.swapit.oopswap.data.datasource.remote.service

import com.swapit.oopswap.data.datasource.remote.dto.request.product.ProductRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.product.ProductResultResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.product.detail.slelect.ProductSelectResultResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @POST("api/user/goods/register")
    suspend fun postProduct(
        @Body product: ProductRequest,
    ): BaseResponse<Long>

    @PUT("api/user/goods/{goodsId}")
    suspend fun editProduct(
        @Path("goodsId") goodsId: Long,
        @Body product: ProductRequest,
    ): BaseResponse<Unit>

    @DELETE("api/user/goods/{goodsId}")
    suspend fun deleteProduct(
        @Path("goodsId") goodsId: Long,
    ): BaseResponse<Unit>

    @DELETE("api/user/goods/{goodsId}/images/{imagesId}")
    suspend fun deleteProductImage(
        @Path("goodsId") goodsId: Long,
        @Path("imagesId") imagesId: Long,
    ): BaseResponse<Unit>

    @Multipart
    @POST("api/user/goods/{goodsId}/images")
    suspend fun postProductImages(
        @Path("goodsId") goodsId: Long,
        @Part images: List<MultipartBody.Part>,
    ): BaseResponse<Unit>

    @GET("/api/user/goods/my/onsale")
    suspend fun myOnSaleProductList(): BaseResponse<ProductSelectResultResponse>

    @GET("/api/user/goods/my/soldout")
    suspend fun mySoldOutProductList(): BaseResponse<ProductSelectResultResponse>

    @GET("/api/all/goods/{goodsId}")
    suspend fun productDetail(
        @Path("goodsId") goodsId: String,
    ): BaseResponse<ProductDetailResponse>

    @GET("api/all/goods")
    suspend fun productsList(
        @Query("cursorId") cursorId: Long?,
        @Query("createdAt") createdAt: String?,
        @Query("cursorValue") cursorValue: Long?,
        @Query("sortBy") sortBy: String?,
        @Query("keyword") keyword: String?,
        @Query("categoryIds") categoryIds: List<Int>?,
    ): BaseResponse<ProductResultResponse>
}
