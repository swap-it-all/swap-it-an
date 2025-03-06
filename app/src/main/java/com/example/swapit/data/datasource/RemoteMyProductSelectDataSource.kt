package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductResponse
import com.example.swapit.data.datasource.remote.service.ProductService

class RemoteMyProductSelectDataSource(private val myProductSelectService: ProductService) {
    suspend fun myProductSelectResponse(): BaseResponse<List<ShoppingProductResponse>> {
        return myProductSelectService.myProductSelectResponse()
    }
}
