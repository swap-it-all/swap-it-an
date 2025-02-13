package com.example.swap_it.domain.repository

import com.example.swap_it.data.datasource.SocialLoginDataSource
import com.example.swap_it.data.datasource.remote.ServiceModule
import com.example.swap_it.data.repository.DefaultSocialLoginRepository
import com.example.swap_it.domain.model.SocialLoginToken

interface SocialLoginRepository {
    suspend fun loginWithKakao(token: String): SocialLoginToken

    suspend fun loginWithGoogle(token: String): SocialLoginToken

    companion object {
        private var instance: SocialLoginRepository? = null

        fun instance(): SocialLoginRepository {
            if (instance == null) {
                instance = DefaultSocialLoginRepository(
                    SocialLoginDataSource(ServiceModule.loginService)
                )
            }
            return instance!!
        }
    }
}
