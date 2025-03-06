package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResults
import com.example.swapit.domain.repository.ShoppingRepository

class DefaultShoppingRepository(
    private val remoteSource: RemoteShoppingDataSource,
) :
    ShoppingRepository {
    override suspend fun shoppingCardResults(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): ShoppingProductResults {
        return remoteSource.shoppingProductResponse(
            cursorId = cursorId,
            createdAt = createdAt,
            cursorValue = cursorValue,
            sortBy = sortBy,
            keyword = keyword,
            categoryIds = categoryIds,
        ).results.toDomain()
    }

    override suspend fun shoppingCardProducts(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): List<ShoppingProduct> {
        return shoppingCardResults(
            cursorId = cursorId,
            createdAt = createdAt,
            cursorValue = cursorValue,
            sortBy = sortBy,
            keyword = keyword,
            categoryIds = categoryIds,
        ).goodsList.map { it.toDomain() }
    }
}
