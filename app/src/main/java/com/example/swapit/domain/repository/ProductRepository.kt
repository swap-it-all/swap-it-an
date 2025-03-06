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
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResults

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

    suspend fun myProductSelectResults(): List<ShoppingProduct>

    suspend fun myProductSelectResponse(): BaseResponse<List<ProductResponse>>

    suspend fun shoppingDetailResults(goodsId: String): ProductDetailResponse

    suspend fun shoppingCardResults(
        cursorId: Long? = null,
        createdAt: String? = null,
        cursorValue: Long? = null,
        sortBy: String? = null,
        keyword: String? = null,
        categoryIds: List<Int>? = null,
    ): ShoppingProductResults

    suspend fun shoppingCardProducts(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): List<ShoppingProduct>


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
