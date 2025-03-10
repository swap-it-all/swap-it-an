package com.example.swapit.data.mapper

import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapProductsResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapProductsResultResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.ReceivedSwapResponse
import com.example.swapit.data.datasource.remote.dto.response.swap.SentSwapResponse
import com.example.swapit.domain.model.swap.ReceivedSwap
import com.example.swapit.domain.model.swap.ReceivedSwapProduct
import com.example.swapit.domain.model.swap.ReceivedSwapProductsResult
import com.example.swapit.domain.model.swap.SentSwap

fun ReceivedSwapProductsResponse.toDomain(): ReceivedSwapProduct {
    return ReceivedSwapProduct(
        goodsId = this.goodsId,
        title = this.title,
        price = this.price,
        category = this.category,
        photoUrl = this.photoUrl,
        placeName = this.placeName,
        createdAt = this.createdAt,
    )
}

fun ReceivedSwapProductsResultResponse.toDomain(): ReceivedSwapProductsResult {
    return ReceivedSwapProductsResult(
        myGoodsTitle = this.myGoodsTitle,
        goodsList = this.goodsList,
    )
}

fun ReceivedSwapResponse.toDomain(): ReceivedSwap {
    return ReceivedSwap(
        goodsId = this.goodsId,
        title = this.title,
        price = this.price,
        category = this.category,
        photoUrl = this.photoUrl,
        placeName = this.placeName,
        viewCount = this.viewCount,
        requestCount = this.requestCount,
        inProgressCount = this.inProgressCount,
        createdAt = this.createdAt,
    )
}

fun SentSwapResponse.toDomain(): SentSwap {
    return SentSwap(
        tradesId = this.tradesId,
        goodsId = this.goodsId,
        title = this.title,
        price = this.price,
        category = this.category,
        placeName = this.placeName,
        myGoodsPhotoUrl = this.myGoodsPhotoUrl,
        targetGoodsPhotoUrl = this.targetGoodsPhotoUrl,
        targetGoodsViewCount = this.targetGoodsViewCount,
        createdAt = this.createdAt,
    )
}