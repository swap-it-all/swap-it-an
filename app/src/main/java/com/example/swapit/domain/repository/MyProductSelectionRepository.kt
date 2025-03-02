package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteMyProductSelectDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductResponse
import com.example.swapit.data.repository.DefaultMyProductSelectRepository
import com.example.swapit.domain.model.shopping.ShoppingProduct

interface MyProductSelectRepository {
    suspend fun myProductSelectResults(): List<ShoppingProduct>
    suspend fun myProductSelectResponse(): BaseResponse<List<ShoppingProductResponse>>

    companion object {
        @Volatile
        private var instance: MyProductSelectRepository? = null

        fun instance(): MyProductSelectRepository {
            return instance ?: synchronized(this) {
                instance ?: DefaultMyProductSelectRepository(
                    remoteSource = RemoteMyProductSelectDataSource(ServiceModule.myProductSelectService),
                ).also { instance = it }
            }
        }
    }
}