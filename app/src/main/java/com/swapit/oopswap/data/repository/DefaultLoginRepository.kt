package com.swapit.oopswap.data.repository

import android.util.Log
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
            localSource.clearTokens()
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

        return try {
            val response = remoteSource.deleteAccount("Bearer $authToken", kakaoToken, reason)
            Log.d("LoginRepository", "deleteAccount() 응답 - 성공: ${response.results}, 메시지: ${response.message}")

            response.success
        } catch (e: Exception) {
            Log.e("LoginRepository", "deleteAccount() 요청 실패", e)
            false
        }
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
