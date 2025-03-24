package com.swapit.company.data.datasource

import com.swapit.company.data.datasource.remote.dto.request.product.ProductRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.product.ProductResultResponse
import com.swapit.company.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.swapit.company.data.datasource.remote.dto.response.product.detail.slelect.ProductSelectResultResponse
import com.swapit.company.data.datasource.remote.service.ProductService
import okhttp3.MultipartBody

class RemoteProductDataSource(private val productService: ProductService) {
    suspend fun postProduct(product: ProductRequest): BaseResponse<Long> = productService.postProduct(product = product)

    suspend fun postProductImages(
        goodsId: Long,
        images: List<MultipartBody.Part>,
    ): BaseResponse<Unit> = productService.postProductImages(goodsId = goodsId, images = images)

    suspend fun deleteProductImage(
        goodsId: Long,
        imagesId: Long,
    ): BaseResponse<Unit> {
        return productService.deleteProductImage(goodsId = goodsId, imagesId = imagesId)
    }

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

    suspend fun editProduct(
        goodsId: Long,
        product: ProductRequest,
    ): BaseResponse<Unit> {
        return productService.editProduct(goodsId = goodsId, product = product)
    }

    suspend fun deleteProduct(goodsId: Long): BaseResponse<Unit> {
        return productService.deleteProduct(goodsId = goodsId)
    }
}
