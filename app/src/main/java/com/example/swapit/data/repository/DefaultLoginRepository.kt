package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteLoginDataSource
import com.example.swapit.data.datasource.local.LocalLoginDataSource
import com.example.swapit.data.datasource.remote.dto.response.login.LoginResponse
import com.example.swapit.domain.model.LoginToken
import com.example.swapit.domain.repository.LoginRepository

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
}

// mapper
private fun LoginResponse.toDomain(): LoginToken {
    return LoginToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        key = this.key,
    )
}
