package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.request.product.ProductRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.service.ProductService
import okhttp3.MultipartBody

class RemoteProductDataSource(private val productService: ProductService) {
    suspend fun postProduct(
        product: ProductRequest,
    ): BaseResponse<Long> = productService.postProduct(product = product)

    suspend fun postProductImages(
        goodsId: Long,
        images: List<MultipartBody.Part>,
    ): BaseResponse<Unit> = productService.postProductImages(goodsId = goodsId, images = images)
}
