package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingCardProduct
import com.example.swapit.data.datasource.remote.dto.response.shopping.ShoppingCardProductsResults
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.ShoppingProductResults
import com.example.swapit.domain.repository.ShoppingRepository

class DefaultShoppingRepository(
    private val remoteSource: RemoteShoppingDataSource,
) :
    ShoppingRepository {
    override suspend fun shoppingCardResults(): ShoppingProductResults {
        return remoteSource.shoppingProductResponse().results.toDomain()
    }

    override suspend fun shoppingCardProducts(): List<ShoppingProduct> {
        return shoppingCardResults().goodsList.map { it.toDomain() }
    }
}

fun ShoppingCardProduct.toDomain(): ShoppingProduct {
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

fun ShoppingCardProductsResults.toDomain(): ShoppingProductResults {
    return ShoppingProductResults(
        goodsList = this.goodsList,
        hasNext = this.hasNext,
        lastCursorId = this.lastCursorId,
        count = this.size,
    )
}
