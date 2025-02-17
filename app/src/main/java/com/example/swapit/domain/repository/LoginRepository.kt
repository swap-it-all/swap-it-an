package com.example.swapit.domain.repository

import android.content.Context
import com.example.swapit.data.datasource.RemoteLoginDataSource
import com.example.swapit.data.datasource.local.LocalLoginDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.repository.DefaultLoginRepository
import com.example.swapit.domain.model.LoginToken

interface LoginRepository {
    suspend fun loginWithKakao(token: String): LoginToken

    suspend fun loginWithGoogle(token: String): LoginToken

    suspend fun refresh(refreshToken: String): LoginToken

    suspend fun logout(refreshToken: String): Boolean

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
