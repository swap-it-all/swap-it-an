package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.service.ShoppingService
import com.example.swapit.domain.model.shopping.ShoppingProductResponse
import com.example.swapit.domain.model.shopping.toDomainModel

class RemoteShoppingDataSource(private val shoppingService: ShoppingService){
    suspend fun getRemoteShoppingProductResponse(): ShoppingProductResponse {
        val response = shoppingService.getGoodsListResponse().toDomainModel()
        return response
    }
}
