package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.datasource.RemoteShoppingDetailDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailDataResponse
import com.example.swapit.data.repository.DefaultShoppingDetailRepository
import com.example.swapit.data.repository.DefaultShoppingRepository
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResults
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailImage
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailUser
import com.example.swapit.ui.navigation.NavItem

interface ShoppingDetailRepository {
    suspend fun shoppingDetailResults(goodsId: String): ShoppingDetailDataResponse

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