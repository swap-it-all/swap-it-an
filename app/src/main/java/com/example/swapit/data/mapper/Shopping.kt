package com.example.swapit.data.mapper

import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingProductsResultsResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.detail.ShoppingDetailDataResponse
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResults
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailData

fun ShoppingProductResponse.toDomain(): ShoppingProduct {
    return ShoppingProduct(
        goodsId = this.goodsId,
        title = this.title,
        price = this.price,
        category = this.category,
        imageUrl = this.imageUrl,
        placeName = this.placeName,
        viewCount = this.viewCount,
        createdAt = this.createdAt,
    )
}

fun ShoppingProductsResultsResponse.toDomain(): ShoppingProductResults {
    return ShoppingProductResults(
        goodsList = this.goodsList,
        hasNext = this.hasNext,
        lastCursorId = this.lastCursorId,
        count = this.size,
    )
}

