package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailDataResponse
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResults
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailImage
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailUser
import com.example.swapit.domain.repository.ShoppingDetailRepository
import com.example.swapit.domain.repository.ShoppingRepository

class DefaultShoppingRepository(
    private val remoteSource: RemoteShoppingDataSource,
) :
    ShoppingRepository {
    override suspend fun shoppingCardResults(): ShoppingProductResults {
        return remoteSource.shoppingProductResponse().results.toDomain()
    }

    override suspend fun shoppingCardProducts(): List<ShoppingProduct> {
        return shoppingCardResults().goodsList.map { it.toDomain() }
    }
}


