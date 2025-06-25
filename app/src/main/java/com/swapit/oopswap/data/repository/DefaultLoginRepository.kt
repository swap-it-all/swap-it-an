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
) : LoginRepository {
    override suspend fun loginWithKakao(token: String): LoginToken {
        val tokens = remoteSource.loginWithKakao(token).toDomain()
        saveTokens(tokens.accessToken, tokens.refreshToken)
        TokenStateManager.tokenFlow.value =
            TokenStateManager.TokenState.Valid(
                tokens.accessToken to tokens.refreshToken,
            )
        return tokens
    }

    override suspend fun loginWithGoogle(token: String): LoginToken {
        val tokens = remoteSource.loginWithGoogle(token).toDomain()
        saveTokens(tokens.accessToken, tokens.refreshToken)
        return tokens
    }

    override suspend fun refresh(refreshToken: String): LoginToken {
        val tokens = remoteSource.refresh(refreshToken).toDomain()
        saveTokens(tokens.accessToken, tokens.refreshToken)
        return tokens
    }

    override suspend fun logout(refreshToken: String): Boolean {
        val isSuccess = remoteSource.logout(refreshToken)
        if (isSuccess.success) {
            // 1) SharedPreferences 비우기
            localSource.clearTokens()
            // 2) TokenStateManager 초기화
            TokenStateManager.tokenFlow.value = TokenStateManager.TokenState.Idle
        }
        return isSuccess.success
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
    ): Boolean {
        Log.d("LoginRepository", "deleteAccount() 요청 - authToken: $authToken, kakaoToken: $kakaoToken, reason: $reason")
        val response = remoteSource.deleteAccount(authToken, kakaoToken, reason)
        Log.d("LoginRepository", "deleteAccount() 응답 - 성공: ${response.results}, 메시지: ${response.message}")
        if (response.success) {
            // SharedPreferences 비우기
            localSource.clearTokens()
            // TokenStateManager 초기화
            TokenStateManager.tokenFlow.value = TokenStateManager.TokenState.Idle
        }
        return response.success
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
