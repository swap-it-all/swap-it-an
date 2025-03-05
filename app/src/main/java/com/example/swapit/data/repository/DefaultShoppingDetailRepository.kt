package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteShoppingDetailDataSource
import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailDataResponse
import com.example.swapit.domain.repository.ShoppingDetailRepository

class DefaultShoppingDetailRepository(
    private val remoteSource: RemoteShoppingDetailDataSource,
) :
    ShoppingDetailRepository {
    override suspend fun shoppingDetailResults(goodsId: String): ShoppingDetailDataResponse {
        return remoteSource.shoppingDetailResponse(goodsId).results
    }
}
