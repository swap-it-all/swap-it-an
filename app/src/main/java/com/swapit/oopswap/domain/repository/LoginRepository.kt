package com.swapit.oopswap.domain.repository

import android.content.Context
import com.swapit.oopswap.data.datasource.RemoteLoginDataSource
import com.swapit.oopswap.data.datasource.local.LocalLoginDataSource
import com.swapit.oopswap.data.datasource.remote.ServiceModule
import com.swapit.oopswap.data.repository.DefaultLoginRepository
import com.swapit.oopswap.domain.model.LoginToken

interface LoginRepository {
    suspend fun loginWithKakao(token: String): LoginToken

    suspend fun loginWithGoogle(token: String): LoginToken

    suspend fun refresh(refreshToken: String): LoginToken

    suspend fun logout(refreshToken: String): Boolean

    suspend fun deleteAccount(
        authToken: String,
        kakaoToken: String,
        reason: String,
    ): Boolean

    suspend fun saveKakaoToken(kakaoToken: String) // 추가

    fun getKakaoToken(): String? // 추가

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    )

    fun accessToken(): String?

    fun refreshToken(): String?

    companion object {
        private var instance: LoginRepository? = null

        fun instance(context: Context): LoginRepository {
            if (instance == null) {
                instance =
                    DefaultLoginRepository(
                        RemoteLoginDataSource(ServiceModule.loginService),
                        LocalLoginDataSource(context),
                    )
            }
            return instance!!
        }
    }
}
