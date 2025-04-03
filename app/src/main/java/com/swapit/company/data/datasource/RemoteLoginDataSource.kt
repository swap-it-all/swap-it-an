package com.swapit.company.data.datasource

import com.swapit.company.data.datasource.remote.dto.request.login.WithDrawRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.login.LoginResponse
import com.swapit.company.data.datasource.remote.service.LoginService

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

    suspend fun logout(refreshToken: String): BaseResponse<Unit> {
        val response = loginService.logout("Bearer $refreshToken")

        return response
    }

    suspend fun deleteAccount(
        authToken: String,
        kakaoToken: String,
        reason: String,
    ): BaseResponse<Unit> {
        val response = loginService.deleteAccount("Bearer $authToken", kakaoToken, WithDrawRequest(reason))
        return response
    }
}
