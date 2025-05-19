package com.swapit.oopswap.domain.repository

import android.content.Context
import coil3.Uri
import com.swapit.oopswap.data.datasource.RemoteProductDataSource
import com.swapit.oopswap.data.datasource.local.model.post.QualityOption
import com.swapit.oopswap.data.datasource.remote.ServiceModule
import com.swapit.oopswap.data.datasource.remote.dto.request.product.ProductRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.product.detail.slelect.ProductSelectResultResponse
import com.swapit.oopswap.data.repository.DefaultProductRepository
import com.swapit.oopswap.domain.model.product.Product
import com.swapit.oopswap.domain.model.product.ProductResults
import com.swapit.oopswap.domain.model.product.detail.select.ProductSelect

interface ProductRepository {
    suspend fun postProduct(
        title: String,
        price: Int,
        quality: QualityOption,
        categoryId: Int,
        description: String,
        placeName: String,
    ): Result<BaseResponse<Long>>

    suspend fun postProductImages(
        goodsId: Long,
        images: List<Uri>,
    ): Result<BaseResponse<Unit>>

    suspend fun deleteProductImage(
        goodsId: Long,
        imagesId: Long,
    ): Result<BaseResponse<Unit>>

    suspend fun editProduct(
        goodsId: Long,
        product: ProductRequest,
    ): Result<BaseResponse<Unit>>

    suspend fun deleteProduct(goodsId: Long): Result<BaseResponse<Unit>>

    suspend fun myOnSaleProductSelectResults(): Result<List<ProductSelect>>

    suspend fun myOnSaleProductSelectResponse(): Result<BaseResponse<ProductSelectResultResponse>>

    suspend fun mySoldOutProductSelectResults(): Result<List<ProductSelect>>

    suspend fun mySoldOutProductSelectResponse(): Result<BaseResponse<ProductSelectResultResponse>>

    suspend fun productDetailResults(goodsId: String): Result<ProductDetailResponse>

    suspend fun productCardResults(
        cursorId: Long? = null,
        createdAt: String? = null,
        cursorValue: Long? = null,
        sortBy: String? = null,
        keyword: String? = null,
        categoryIds: List<Int>? = null,
    ): Result<ProductResults>

    suspend fun productCardProducts(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): Result<List<Product>>

    companion object {
        private var instance: ProductRepository? = null

        fun instance(context: Context, onLogout: () -> Unit = {}): ProductRepository {
            if (instance == null) {
                instance =
                    DefaultProductRepository(
                        remoteSource = RemoteProductDataSource(ServiceModule.productService),
                        context = context,
                        onLogout = onLogout
                    )
            }
            return instance!!
        }
    }
}
