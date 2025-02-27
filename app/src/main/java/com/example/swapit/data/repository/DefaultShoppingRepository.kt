package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResponse
import com.example.swapit.domain.model.shopping.ShoppingProductResults
import com.example.swapit.domain.model.shopping.toDomainModel
import com.example.swapit.domain.repository.ShoppingRepository

class DefaultShoppingRepository(
    private val remoteSource: RemoteShoppingDataSource,
) :
    ShoppingRepository {
    override suspend fun getShoppingResponse(): ShoppingProductResponse {
        return remoteSource.getRemoteShoppingProductResponse()
    }

    override suspend fun getShoppingResults(shoppingProductResponse: ShoppingProductResponse): ShoppingProductResults {
        val results = shoppingProductResponse.results.toDomainModel()
        return results
    }

}

