package com.swapit.company.data.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.swapit.company.data.datasource.RemoteUserDataSource
import com.swapit.company.data.mapper.toDomain
import com.swapit.company.domain.model.user.UserInfo
import com.swapit.company.domain.repository.UserRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class DefaultUserRepository(
    private val remoteSource: RemoteUserDataSource,
    private val context: Context,
) : UserRepository {
    override suspend fun myUserInfo(): UserInfo {
        return remoteSource.myUserInfo().toDomain()
    }

    override suspend fun updateNickname(nickname: String) {
        remoteSource.updateNickname(nickname)
    }

    override suspend fun updateProfileImage(image: Uri) {
        val imageFile = toFile(context, image)
        remoteSource.updateProfileImage(createMultipartBody(imageFile))
    }



    private fun createMultipartBody(file: File): MultipartBody.Part {
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }

    private fun createTempFile(fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    private fun copyToFile(
        context: Context,
        uri: Uri,
        file: File,
    ) {
        val inputStream = context.contentResolver.openInputStream(uri)
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
        val ext = context.contentResolver.getType(uri)!!.split("/").last()

        return "$name.$ext"
    }
}
