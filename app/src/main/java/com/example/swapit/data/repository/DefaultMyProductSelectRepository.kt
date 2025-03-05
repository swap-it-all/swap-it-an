package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteMyProductSelectDataSource
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductResponse
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.repository.MyProductSelectRepository

class DefaultMyProductSelectRepository(private val remoteSource: RemoteMyProductSelectDataSource) :
    MyProductSelectRepository {
    override suspend fun myProductSelectResults(): List<ShoppingProduct> {
        return remoteSource.myProductSelectResponse().results.map { it.toDomain() }
    }

    override suspend fun myProductSelectResponse(): BaseResponse<List<ShoppingProductResponse>> {
        return remoteSource.myProductSelectResponse()
    }
}
