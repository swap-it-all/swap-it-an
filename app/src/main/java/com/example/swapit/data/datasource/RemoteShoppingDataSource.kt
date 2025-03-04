package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductsResultsResponse
import com.example.swapit.data.datasource.remote.service.ShoppingService

class RemoteShoppingDataSource(private val shoppingService: ShoppingService) {
    suspend fun shoppingProductResponse(): BaseResponse<ShoppingProductsResultsResponse> {
        return shoppingService.shoppingProductsResponse()
    }
}
