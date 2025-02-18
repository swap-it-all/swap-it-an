package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.request.product.ProductRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.service.ProductService
import okhttp3.MultipartBody

class RemoteProductDataSource(private val productService: ProductService) {
    suspend fun postProduct(
        product: ProductRequest,
    ): BaseResponse<Long> = productService.postProduct(product = product, token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWdvMDcyNEBuYXZlci5jb20iLCJpYXQiOjE3Mzk4NTYyNzQsImV4cCI6MTczOTg1ODA3NH0.606uaZ_CqYSg0ugAG-uRSCE5GiJmcsUMHHyvcadbmYE")

    suspend fun postProductImages(
        goodsId: Long,
        images: List<MultipartBody.Part>,
    ): BaseResponse<Unit> = productService.postProductImages(goodsId = goodsId, images = images, token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWdvMDcyNEBuYXZlci5jb20iLCJpYXQiOjE3Mzk4NTYyNzQsImV4cCI6MTczOTg1ODA3NH0.606uaZ_CqYSg0ugAG-uRSCE5GiJmcsUMHHyvcadbmYE")
}
