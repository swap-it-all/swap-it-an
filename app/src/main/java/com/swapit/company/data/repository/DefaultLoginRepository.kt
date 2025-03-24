package com.swapit.company.data.repository

import com.swapit.company.data.datasource.RemoteLoginDataSource
import com.swapit.company.data.datasource.local.LocalLoginDataSource
import com.swapit.company.data.datasource.remote.ServiceModule.loginService
import com.swapit.company.data.datasource.remote.dto.response.login.LoginResponse
import com.swapit.company.domain.model.LoginToken
import com.swapit.company.domain.repository.LoginRepository

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
        if (isSuccess) {
            localSource.clearTokens()
        }
        return isSuccess
    }

    override suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        localSource.saveTokens(accessToken, refreshToken)
    }

    override fun accessToken(): String? = localSource.accessToken()

    override fun refreshToken(): String? = localSource.refreshToken()
    override suspend fun deleteAccount(authToken: String, kakaoToken: String,reason: String): Boolean {
        return try {
            val response = remoteSource.deleteAccount(authToken,kakaoToken,reason)
            response // 서버 응답에 따른 성공 여부 반환
        } catch (e: Exception) {
            println("회원 탈퇴 실패: ${e.message}")
            false // 실패 시 false 반환
        }
    }

    override suspend fun saveKakaoToken(kakaoToken: String) {
        localSource.saveKakaoToken(kakaoToken)
    }

    override fun getKakaoToken(): String? = localSource.getKakaoToken()

}

// mapper
private fun LoginResponse.toDomain(): LoginToken {
    return LoginToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        key = this.key,
    )
}
