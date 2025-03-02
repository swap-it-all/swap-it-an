package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductResponse
import com.example.swapit.data.datasource.remote.service.MyProductSelectService

class RemoteMyProductSelectDataSource(private val myProductSelectService: MyProductSelectService) {
    suspend fun myProductSelectResponse(): BaseResponse<List<ShoppingProductResponse>> {
        return myProductSelectService.myProductSelectResponse()
    }
}