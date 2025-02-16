package com.example.swapit.domain.repository

import android.content.Context
import com.example.swapit.data.datasource.SocialLoginDataSource
import com.example.swapit.data.datasource.local.LocalSocialLoginDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.repository.DefaultSocialLoginRepository
import com.example.swapit.domain.model.SocialLoginToken

interface SocialLoginRepository {
    suspend fun loginWithKakao(token: String): SocialLoginToken

    suspend fun loginWithGoogle(token: String): SocialLoginToken

    suspend fun refresh(refreshToken: String): SocialLoginToken

    suspend fun logout(refreshToken: String): Boolean

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    )

    fun accessToken(): String?

    fun refreshToken(): String?

    companion object {
        private var instance: SocialLoginRepository? = null

        fun instance(context: Context): SocialLoginRepository {
            if (instance == null) {
                instance =
                    DefaultSocialLoginRepository(
                        SocialLoginDataSource(ServiceModule.loginService),
                        LocalSocialLoginDataSource(context),
                    )
            }
            return instance!!
        }
    }
}
