package com.example.swap_it.data.repository

import com.example.swap_it.data.datasource.SocialLoginDataSource
import com.example.swap_it.data.datasource.remote.dto.response.login.SocialLoginResponse
import com.example.swap_it.domain.model.SocialLoginToken
import com.example.swap_it.domain.repository.SocialLoginRepository

class DefaultSocialLoginRepository(
    private val remoteSource: SocialLoginDataSource,
) : SocialLoginRepository {

    override suspend fun loginWithKakao(token: String): SocialLoginToken {
        return remoteSource.loginWithKakao(token).toDomain()
    }

    override suspend fun loginWithGoogle(token: String): SocialLoginToken {
        return remoteSource.loginWithGoogle(token).toDomain()
    }
}

// mapper
private fun SocialLoginResponse.toDomain(): SocialLoginToken {
    return SocialLoginToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        key = this.key,
    )
}
