package com.example.swapit.data.repository

import com.example.swapit.data.datasource.SocialLoginDataSource
import com.example.swapit.data.datasource.local.LocalSocialLoginDataSource
import com.example.swapit.data.datasource.remote.dto.response.login.SocialLoginResponse
import com.example.swapit.domain.model.SocialLoginToken
import com.example.swapit.domain.repository.SocialLoginRepository

class DefaultSocialLoginRepository(
    private val remoteSource: SocialLoginDataSource,
    private val localSource: LocalSocialLoginDataSource,
) : SocialLoginRepository {
    override suspend fun loginWithKakao(token: String): SocialLoginToken {
        val tokens = remoteSource.loginWithKakao(token).toDomain()
        saveTokens(tokens.accessToken, tokens.refreshToken)
        return tokens
    }

    override suspend fun loginWithGoogle(token: String): SocialLoginToken {
        val tokens = remoteSource.loginWithGoogle(token).toDomain()
        saveTokens(tokens.accessToken, tokens.refreshToken)
        return tokens
    }

    override suspend fun refresh(refreshToken: String): SocialLoginToken {
        return remoteSource.refresh(refreshToken).toDomain()
    }

    override suspend fun logout(refreshToken: String): Boolean {
        val isSuccess = remoteSource.logout(refreshToken)
        if (isSuccess) {
            localSource.clearTokens()
        }
        return isSuccess
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        localSource.saveTokens(accessToken, refreshToken)
    }

    override fun accessToken(): String? = localSource.accessToken()

    override fun refreshToken(): String? = localSource.refreshToken()
}

// mapper
private fun SocialLoginResponse.toDomain(): SocialLoginToken {
    return SocialLoginToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        key = this.key,
    )
}
