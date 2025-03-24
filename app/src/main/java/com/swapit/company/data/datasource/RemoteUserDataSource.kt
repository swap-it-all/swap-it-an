package com.swapit.company.data.datasource

import com.swapit.company.data.datasource.remote.dto.request.user.UserProfileRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.user.UserResponse
import com.swapit.company.data.datasource.remote.service.UserService
import okhttp3.MultipartBody

class RemoteUserDataSource(
    private val userService: UserService,
) {
    suspend fun myUserInfo(): UserResponse {
        val response = userService.myUserInfo()

        return if (response.success) {
            response.results
        } else {
            throw Exception(response.message)
        }
    }

    suspend fun updateNickname(nickname: String): BaseResponse<Unit> {
        val response = userService.updateNickname(UserProfileRequest(nickname))

        return if (response.success) {
            response
        } else {
            throw Exception(response.message)
        }
    }

    suspend fun updateProfileImage(image: MultipartBody.Part): BaseResponse<Unit> {
        val response = userService.updateProfileImage(image)

        return if (response.success) {
            response
        } else {
            throw Exception(response.message)
        }
    }
}
