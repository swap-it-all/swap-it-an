package com.swapit.oopswap.data.repository

import android.util.Log
import com.swapit.oopswap.data.auth.TokenStateManager
import com.swapit.oopswap.data.datasource.RemoteLoginDataSource
import com.swapit.oopswap.data.datasource.local.LocalLoginDataSource
import com.swapit.oopswap.data.datasource.remote.dto.response.login.LoginResponse
import com.swapit.oopswap.domain.model.LoginToken
import com.swapit.oopswap.domain.repository.LoginRepository

class DefaultLoginRepository(
    private val remoteSource: RemoteLoginDataSource,
    private val localSource: LocalLoginDataSource,
    private val onLogout: () -> Unit
) : LoginRepository {

    override suspend fun loginWithKakao(token: String): Result<LoginToken> =
        safeApiCall(onLogout) {
            val tokens = remoteSource.loginWithKakao(token).toDomain()
            saveTokens(tokens.accessToken, tokens.refreshToken)
            TokenStateManager.tokenFlow.value =
                TokenStateManager.TokenState.Valid(
                    tokens.accessToken to tokens.refreshToken,
                )
            tokens
        }

    override suspend fun loginWithGoogle(token: String): Result<LoginToken> =
        safeApiCall(onLogout) {
            val tokens = remoteSource.loginWithGoogle(token).toDomain()
            saveTokens(tokens.accessToken, tokens.refreshToken)
            tokens
        }

    override suspend fun refresh(refreshToken: String): Result<LoginToken> =
        safeApiCall(onLogout) {
            val tokens = remoteSource.refresh(refreshToken).toDomain()
            saveTokens(tokens.accessToken, tokens.refreshToken)
            tokens
        }

    override suspend fun logout(refreshToken: String): Result<Boolean> =
        safeApiCall(onLogout) {
            val response = remoteSource.logout(refreshToken)
            if (response.success) {
                // 1) SharedPreferences 비우기
                localSource.clearTokens()
                // 2) TokenState 초기화
                TokenStateManager.tokenFlow.value = TokenStateManager.TokenState.Idle
            }
            response.success
        }

    override suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        localSource.saveTokens(accessToken, refreshToken)
    }

    override fun accessToken(): String? = localSource.accessToken()

    override fun refreshToken(): String? = localSource.refreshToken()

    override suspend fun deleteAccount(
        authToken: String,
        kakaoToken: String,
        reason: String,
    ): Result<Boolean> =
        safeApiCall(onLogout) {
            Log.d("LoginRepository", "deleteAccount() 요청 - authToken: $authToken, kakaoToken: $kakaoToken, reason: $reason")
            val response = remoteSource.deleteAccount("Bearer $authToken", kakaoToken, reason)
            Log.d("LoginRepository", "deleteAccount() 응답 - 성공: ${response.results}, 메시지: ${response.message}")
            response.success
        }

    override suspend fun saveKakaoToken(kakaoToken: String) {
        localSource.saveKakaoToken(kakaoToken)
    }

    override fun getKakaoToken(): String? = localSource.getKakaoToken()
}

// mapper
private fun LoginResponse.toDomain(): LoginToken =
    LoginToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        key = this.key,
    )
