package com.swapit.oopswap.data.datasource

import android.util.Log
import com.swapit.oopswap.data.datasource.remote.dto.request.login.WithDrawRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.login.LoginResponse
import com.swapit.oopswap.data.datasource.remote.service.LoginService

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
        val stackTrace = Throwable().stackTrace
        val callerInfo = stackTrace.getOrNull(1) // 0: 현재 함수, 1: 호출자
        Log.d("Caller", "refresh() was called from: ${callerInfo?.className}.${callerInfo?.methodName}(${callerInfo?.fileName}:${callerInfo?.lineNumber})")

        val response = loginService.refreshToken("Bearer $refreshToken")

        return if (response.success) {
            Log.d("Auth", "refresh success${response.results}")
            response.results
        } else {
            Log.e("Auth", "refresh fail${response.results}")
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
