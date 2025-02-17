package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.response.login.LoginResponse
import com.example.swapit.data.datasource.remote.service.LoginService

class RemoteLoginDataSource(
    private val loginService: LoginService,
) {
    suspend fun loginWithKakao(token: String): LoginResponse {
        val response = loginService.loginWithKakao("Bearer $token")

        return if (response.success) {
            response.results
        } else {
            throw Exception(response.message)
        }
    }

    suspend fun loginWithGoogle(token: String): LoginResponse {
        val response = loginService.loginWithGoogle("Bearer $token")

        return if (response.success) {
            response.results
        } else {
            throw Exception(response.message)
        }
    }

    suspend fun refresh(refreshToken: String): LoginResponse {
        val response = loginService.refreshToken("Bearer $refreshToken")

        return if (response.success) {
            response.results
        } else {
            throw Exception(response.message)
        }
    }

    suspend fun logout(refreshToken: String): Boolean {
        val response = loginService.logout("Bearer $refreshToken")

        return response.success
    }
}
