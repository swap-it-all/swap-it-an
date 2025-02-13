package com.example.swapit.data.datasource

import com.example.swapit.data.datasource.remote.dto.response.login.SocialLoginResponse
import com.example.swapit.data.datasource.remote.service.LoginService

class SocialLoginDataSource(
    private val loginService: LoginService,
) {
    suspend fun loginWithKakao(token: String): SocialLoginResponse {
        val response = loginService.loginWithKakao("Bearer $token")

        return if (response.success) {
            response.results
        } else {
            throw Exception(response.message)
        }
    }

    suspend fun loginWithGoogle(token: String): SocialLoginResponse {
        val response = loginService.loginWithGoogle("Bearer $token")

        return if (response.success) {
            response.results
        } else {
            throw Exception(response.message)
        }
    }
}
