package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.SocialLoginDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.repository.DefaultSocialLoginRepository
import com.example.swapit.domain.model.SocialLoginToken

interface SocialLoginRepository {
    suspend fun loginWithKakao(token: String): SocialLoginToken

    suspend fun loginWithGoogle(token: String): SocialLoginToken

    companion object {
        private var instance: SocialLoginRepository? = null

        fun instance(): SocialLoginRepository {
            if (instance == null) {
                instance =
                    DefaultSocialLoginRepository(
                        SocialLoginDataSource(ServiceModule.loginService),
                    )
            }
            return instance!!
        }
    }
}
