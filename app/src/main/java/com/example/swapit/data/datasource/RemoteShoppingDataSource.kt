package com.example.swapit.data.datasource

import android.util.Log
import com.example.swapit.data.datasource.remote.ServiceModule.productService
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.Goods
import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResults
import com.example.swapit.data.datasource.remote.service.ShoppingService
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResponse
import com.example.swapit.domain.model.shopping.ShoppingProductResults
import okhttp3.MultipartBody

class RemoteShoppingDataSource(private val shoppingService: ShoppingService){
    suspend fun getShoppingData(): GoodsListResponse {
        val response = shoppingService.getShoppingData()
        return response
    }
}

fun Goods.toDomainModel(): ShoppingProduct {
    return ShoppingProduct(
        goodsId = this.id,
        title = this.title,
        price = this.price,
        category = this.category,
        imageUrl = this.imageUrl,
        placeName =  this.placeName,
        viewCount = this.viewCount,
        createdAt = this.createdAt
    )
}

fun GoodsListResponse.toDomainModel(): ShoppingProductResponse {
    return ShoppingProductResponse(
        success = this.success,
        message = this.message,
        results = this.results,
    )
}

fun GoodsListResults.toDomainModel(): ShoppingProductResults {
    return ShoppingProductResults(
        goodsList = this.goods,
        hasNext = this.hasNext,
        lastCursorId = this.lastCursorId,
        size = this.size

    )
}