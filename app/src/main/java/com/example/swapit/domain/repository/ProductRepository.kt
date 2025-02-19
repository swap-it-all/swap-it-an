package com.example.swapit.domain.repository

import android.content.Context
import coil3.Uri
import com.example.swapit.data.datasource.RemoteProductDataSource
import com.example.swapit.data.datasource.local.model.post.QualityOption
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.repository.DefaultProductRepository

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
