package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.repository.DefaultShoppingRepository
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResults

interface ShoppingRepository {
    suspend fun shoppingCardResults(
        cursorId: Long? = null,
        createdAt: String? = null,
        cursorValue: Long? = null,
        sortBy: String? = null,
        keyword: String? = null,
        categoryIds: List<Int>? = null
    ): ShoppingProductResults

    suspend fun shoppingCardProducts(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?
    ): List<ShoppingProduct>

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
