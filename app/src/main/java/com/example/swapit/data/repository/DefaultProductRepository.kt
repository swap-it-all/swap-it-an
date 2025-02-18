package com.example.swapit.data.repository

import android.content.Context
import android.os.Environment
import coil3.Uri
import coil3.toAndroidUri
import com.example.swapit.data.datasource.RemoteProductDataSource
import com.example.swapit.data.datasource.local.model.post.QualityOption
import com.example.swapit.data.datasource.remote.dto.request.product.ProductRequest
import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.domain.repository.ProductRepository
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
        placeName: String
    ): BaseResponse<Long> {
        val productRequest = ProductRequest(
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
        images: List<Uri>
    ): BaseResponse<Unit> {
        val imageFile = images.map { uri ->
            val file = toFile(context, uri) // uri -> file로 변환
            createMultipartBody(file) // file -> multipartBody로 변환
        }
        return remoteSource.postProductImages(goodsId, imageFile)
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
    private fun copyToFile(context: Context, uri: Uri, file: File) {
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

    private fun toFile(context: Context, uri: Uri): File {
        val fileName = getFileName(context, uri)

        val file = createTempFile(fileName)
        copyToFile(context, uri, file)

        return File(file.absolutePath)
    }

    // get file name & extension
    private fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri.toAndroidUri())!!.split("/").last()

        return "$name.$ext"
    }
}

// todo mapper

