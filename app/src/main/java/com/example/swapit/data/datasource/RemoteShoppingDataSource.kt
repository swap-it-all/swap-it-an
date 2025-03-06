package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductsResultsResponse
import com.example.swapit.data.datasource.remote.service.ShoppingService

class RemoteShoppingDataSource(private val shoppingService: ShoppingService) {
    suspend fun shoppingProductResponse(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): BaseResponse<ShoppingProductsResultsResponse> {
        return shoppingService.shoppingProductsResponse(
            cursorId = cursorId,
            createdAt = createdAt,
            cursorValue = cursorValue,
            sortBy = sortBy,
            keyword = keyword,
            categoryIds = categoryIds,
        )
    }
}
