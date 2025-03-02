package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.request.user.UserProfileRequest
import com.example.swapit.data.datasource.remote.dto.response.user.UserResponse
import com.example.swapit.data.datasource.remote.service.UserService

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

    suspend fun updateNickname(nickname: String) {
        val response = userService.updateNickname(UserProfileRequest(nickname))

        if (!response.success) {
            throw Exception(response.message)
        }
    }
}
