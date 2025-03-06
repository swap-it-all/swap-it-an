package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteShoppingDetailDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.example.swapit.data.repository.DefaultShoppingDetailRepository

interface ShoppingDetailRepository {
    suspend fun shoppingDetailResults(goodsId: String): ProductDetailResponse

    companion object {
        @Volatile
        private var instance: ShoppingDetailRepository? = null

        fun instance(): ShoppingDetailRepository {
            return instance ?: synchronized(this) {
                instance ?: DefaultShoppingDetailRepository(
                    remoteSource = RemoteShoppingDetailDataSource(ServiceModule.shoppingDetailService),
                ).also { instance = it }
            }
        }
    }
}
