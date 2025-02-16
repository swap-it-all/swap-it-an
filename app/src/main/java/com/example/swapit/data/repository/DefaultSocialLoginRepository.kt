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
        // 카카오 액세스 토큰 받아서 -> 서버에 전달 -> local에 저장
        val tokens = remoteSource.loginWithKakao(token).toDomain()
        saveTokens(tokens.accessToken, tokens.refreshToken)
        return tokens
    }

    override suspend fun loginWithGoogle(token: String): SocialLoginToken {
        return remoteSource.loginWithGoogle(token).toDomain()
    }

    override suspend fun refresh(refreshToken: String): SocialLoginToken {
        return remoteSource.refresh(refreshToken).toDomain()
    }

    override suspend fun logout(refreshToken: String): Boolean {
        return remoteSource.logout(refreshToken)
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
