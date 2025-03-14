package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.request.product.ProductRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.product.ProductResultResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.slelect.ProductSelectResultResponse
import com.example.swapit.data.datasource.remote.service.ProductService
import okhttp3.MultipartBody

class RemoteProductDataSource(private val productService: ProductService) {
    suspend fun postProduct(product: ProductRequest): BaseResponse<Long> = productService.postProduct(product = product)

    suspend fun postProductImages(
        goodsId: Long,
        images: List<MultipartBody.Part>,
    ): BaseResponse<Unit> = productService.postProductImages(goodsId = goodsId, images = images)

    suspend fun productDetail(goodsId: String): BaseResponse<ProductDetailResponse> {
        return productService.productDetail(goodsId)
    }

    suspend fun productList(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): BaseResponse<ProductResultResponse> {
        return productService.productsList(
            cursorId = cursorId,
            createdAt = createdAt,
            cursorValue = cursorValue,
            sortBy = sortBy,
            keyword = keyword,
            categoryIds = categoryIds,
        )
    }

    suspend fun myOnSaleProductList(): BaseResponse<ProductSelectResultResponse> {
        return productService.myOnSaleProductList()
    }

    suspend fun mySoldOutProductList(): BaseResponse<ProductSelectResultResponse> {
        return productService.mySoldOutProductList()
    }
}
