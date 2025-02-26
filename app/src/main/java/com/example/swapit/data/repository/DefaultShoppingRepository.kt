package com.example.swapit.data.repository

import android.content.Context
import android.os.Environment
import coil3.Uri
import coil3.toAndroidUri
import com.example.swapit.data.datasource.RemoteProductDataSource
import com.example.swapit.data.datasource.RemoteShoppingDataSource
import com.example.swapit.data.datasource.local.model.post.QualityOption
import com.example.swapit.data.datasource.remote.dto.request.product.ProductRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.shopping.GoodsListResponse
import com.example.swapit.domain.repository.ProductRepository
import com.example.swapit.domain.repository.ShoppingRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class DefaultShoppingRepository(
    private val remoteSource: RemoteShoppingDataSource,
) :
    ShoppingRepository {
    override suspend fun getShoppingData(): GoodsListResponse {
        return remoteSource.getShoppingData()
    }
}

