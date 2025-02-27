package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingCardProductsResults
import com.example.swapit.domain.model.shopping.ShoppingProductResults
import com.example.swapit.domain.model.shopping.toDomainModel
import com.example.swapit.domain.repository.ShoppingRepository

class DefaultShoppingRepository(
    private val remoteSource: RemoteShoppingDataSource,
) :
    ShoppingRepository {
    override suspend fun shoppingCardResults(): ShoppingProductResults {
        return remoteSource.shoppingProductResponse().results.toDomainModel()
    }

}
