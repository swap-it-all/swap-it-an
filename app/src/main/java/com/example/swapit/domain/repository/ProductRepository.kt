package com.example.swapit.domain.repository

import android.content.Context
import coil3.Uri
import com.example.swapit.data.datasource.RemoteProductDataSource
import com.example.swapit.data.datasource.local.model.post.QualityOption
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.product.ProductResponse
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.example.swapit.data.repository.DefaultProductRepository
import com.example.swapit.domain.model.product.Product
import com.example.swapit.domain.model.product.ProductResults

interface ProductRepository {
    suspend fun postProduct(
        title: String,
        price: Int,
        quality: QualityOption,
        categoryId: Int,
        description: String,
        placeName: String,
    ): BaseResponse<Long>

    suspend fun postProductImages(
        goodsId: Long,
        images: List<Uri>,
    ): BaseResponse<Unit>

    suspend fun myProductSelectResults(): List<Product>

    suspend fun myProductSelectResponse(): BaseResponse<List<ProductResponse>>

    suspend fun productDetailResults(goodsId: String): ProductDetailResponse

    suspend fun productCardResults(
        cursorId: Long? = null,
        createdAt: String? = null,
        cursorValue: Long? = null,
        sortBy: String? = null,
        keyword: String? = null,
        categoryIds: List<Int>? = null,
    ): ProductResults

    suspend fun productCardProducts(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): List<Product>

    companion object {
        private var instance: ProductRepository? = null

        fun instance(context: Context): ProductRepository {
            if (instance == null) {
                instance =
                    DefaultProductRepository(
                        remoteSource = RemoteProductDataSource(ServiceModule.productService),
                        context = context,
                    )
            }
            return instance!!
        }
    }
}
