package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailDataResponse
import com.example.swapit.data.datasource.remote.service.ShoppingDetailService

class RemoteShoppingDetailDataSource(private val shoppingDetailService: ShoppingDetailService) {
    suspend fun shoppingDetailResponse(goodsId: String): BaseResponse<ShoppingDetailDataResponse> {
        return shoppingDetailService.shoppingDetailResponse(goodsId)
    }
}