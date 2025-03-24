package com.swapit.company.data.repository

import android.content.Context
import android.os.Environment
import coil3.Uri
import coil3.toAndroidUri
import com.swapit.company.data.datasource.RemoteProductDataSource
import com.swapit.company.data.datasource.local.model.post.QualityOption
import com.swapit.company.data.datasource.remote.dto.request.product.ProductRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.product.detail.ProductDetailResponse
import com.swapit.company.data.datasource.remote.dto.response.product.detail.slelect.ProductSelectResultResponse
import com.swapit.company.data.mapper.toDomain
import com.swapit.company.domain.model.product.Product
import com.swapit.company.domain.model.product.ProductResults
import com.swapit.company.domain.model.product.detail.select.ProductSelect
import com.swapit.company.domain.repository.ProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class DefaultProductRepository(
    private val remoteSource: RemoteProductDataSource,
    private val context: Context,
) :
    ProductRepository {
    override suspend fun postProduct(
        title: String,
        price: Int,
        quality: QualityOption,
        categoryId: Int,
        description: String,
        placeName: String,
    ): BaseResponse<Long> {
        val productRequest =
            ProductRequest(
                title = title,
                price = price,
                quality = quality.name,
                categoryId = categoryId,
                description = description,
                placeName = placeName,
            )

        return remoteSource.postProduct(productRequest)
    }

    override suspend fun postProductImages(
        goodsId: Long,
        images: List<Uri>,
    ): BaseResponse<Unit> {
        val imageFile =
            images.map { uri ->
                val file = toFile(context, uri)
                createMultipartBody(file)
            }
        return remoteSource.postProductImages(goodsId, imageFile)
    }

    override suspend fun deleteProductImage(goodsId: Long, imagesId: Long): BaseResponse<Unit> {
        return remoteSource.deleteProductImage(goodsId, imagesId)
    }

    override suspend fun editProduct(goodsId: Long, product: ProductRequest): BaseResponse<Unit> {
        return remoteSource.editProduct(goodsId, product)
    }

    override suspend fun deleteProduct(goodsId: Long): BaseResponse<Unit> {
        return remoteSource.deleteProduct(goodsId)
    }

    private fun createMultipartBody(file: File): MultipartBody.Part {
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("images", file.name, requestBody)
    }

    private fun createTempFile(fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    // 파일 내용 스트림 복사
    private fun copyToFile(
        context: Context,
        uri: Uri,
        file: File,
    ) {
        val inputStream = context.contentResolver.openInputStream(uri.toAndroidUri())
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(4 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }

        outputStream.flush()
        outputStream.close()
    }

    private fun toFile(
        context: Context,
        uri: Uri,
    ): File {
        val fileName = getFileName(context, uri)

        val file = createTempFile(fileName)
        copyToFile(context, uri, file)

        return File(file.absolutePath)
    }

    private fun getFileName(
        context: Context,
        uri: Uri,
    ): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri.toAndroidUri())!!.split("/").last()

        return "$name.$ext"
    }

    override suspend fun productCardResults(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): ProductResults {
        return remoteSource.productList(
            cursorId = cursorId,
            createdAt = createdAt,
            cursorValue = cursorValue,
            sortBy = sortBy,
            keyword = keyword,
            categoryIds = categoryIds,
        ).results.toDomain()
    }

    override suspend fun productCardProducts(
        cursorId: Long?,
        createdAt: String?,
        cursorValue: Long?,
        sortBy: String?,
        keyword: String?,
        categoryIds: List<Int>?,
    ): List<Product> {
        return productCardResults(
            cursorId = cursorId,
            createdAt = createdAt,
            cursorValue = cursorValue,
            sortBy = sortBy,
            keyword = keyword,
            categoryIds = categoryIds,
        ).goodsList.map { it.toDomain() }
    }

    override suspend fun productDetailResults(goodsId: String): ProductDetailResponse {
        return remoteSource.productDetail(goodsId).results
    }

    override suspend fun myOnSaleProductSelectResults(): List<ProductSelect> {
        return remoteSource.myOnSaleProductList().results.data.map { it.toDomain() }
    }

    override suspend fun myOnSaleProductSelectResponse(): BaseResponse<ProductSelectResultResponse> {
        return remoteSource.myOnSaleProductList()
    }

    override suspend fun mySoldOutProductSelectResults(): List<ProductSelect> {
        return remoteSource.mySoldOutProductList().results.data.map { it.toDomain() }
    }

    override suspend fun mySoldOutProductSelectResponse(): BaseResponse<ProductSelectResultResponse> {
        return remoteSource.mySoldOutProductList()
    }
}

// todo mapper
