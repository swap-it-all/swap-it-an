package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.repository.DefaultShoppingRepository
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResults

interface ShoppingRepository {
    suspend fun shoppingCardResults(): ShoppingProductResults

    suspend fun shoppingCardProducts(): List<ShoppingProduct>

    companion object {
        @Volatile
        private var instance: ShoppingRepository? = null

        fun instance(): ShoppingRepository {
            return instance ?: synchronized(this) {
                instance ?: DefaultShoppingRepository(
                    remoteSource = RemoteShoppingDataSource(ServiceModule.shoppingService),
                ).also { instance = it }
            }
        }
    }
}
