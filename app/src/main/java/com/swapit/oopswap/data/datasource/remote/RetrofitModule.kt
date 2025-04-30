package com.swapit.oopswap.data.datasource.remote

import com.swapit.oopswap.BuildConfig
import com.swapit.oopswap.SwapItApplication.Companion.appContext
import com.swapit.oopswap.data.datasource.local.LocalLoginDataSource
import com.swapit.oopswap.data.datasource.remote.interceptor.AuthAuthenticator
import com.swapit.oopswap.data.datasource.remote.interceptor.AuthInterceptor
import com.swapit.oopswap.data.datasource.remote.interceptor.LoggingInterceptor
import com.swapit.oopswap.data.datasource.remote.service.LoginService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.time.Duration

object RetrofitModule {
    private val loginServiceHolder = LoginServiceHolder()

    fun retrofit(): Retrofit {
        val converterFactory =
            jsonConverterFactory(
                Json {
                    coerceInputValues = true
                },
            )
        val client = okHttpClient()

        val retrofit =
            Retrofit
                .Builder()
                .baseUrl(BuildConfig.SWAP_IT_BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .build()

        loginServiceHolder.loginService = retrofit.create(LoginService::class.java)

        return retrofit
    }

    fun okHttpClient(): OkHttpClient {
        val localLoginDataSource = LocalLoginDataSource(appContext)
        val authenticator = AuthAuthenticator(loginServiceHolder, localLoginDataSource, onLogout = {})

        return OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor(localLoginDataSource))
            .authenticator(authenticator)
            .addInterceptor(LoggingInterceptor.create())
            .pingInterval(Duration.ofSeconds(10))
            .build()
    }

    private fun jsonConverterFactory(json: Json): Converter.Factory = json.asConverterFactory("application/json".toMediaType())
}

class LoginServiceHolder {
    var loginService: LoginService? = null
}
