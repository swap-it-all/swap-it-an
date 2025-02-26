package com.example.swapit.domain.repository

import android.content.Context
import com.example.swapit.data.datasource.RemoteProductDataSource
import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.datasource.local.model.post.QualityOption
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResults
import com.example.swapit.data.repository.DefaultShoppingRepository

interface ShoppingRepository {
    suspend fun getShoppingData(): GoodsListResponse

    companion object {
        @Volatile
        private var instance: ShoppingRepository? = null

        fun instance(): ShoppingRepository {
            return instance ?: synchronized(this) {
                instance ?: DefaultShoppingRepository(
                    remoteSource = RemoteShoppingDataSource(ServiceModule.shoppingService)
                ).also { instance = it }
            }
        }
    }
}
