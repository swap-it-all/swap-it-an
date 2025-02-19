package com.example.swapit.data.datasource.remote

import com.example.swapit.BuildConfig
import com.example.swapit.SwapItApplication.Companion.appContext
import com.example.swapit.data.datasource.local.LocalLoginDataSource
import com.example.swapit.data.datasource.remote.interceptor.AuthAuthenticator
import com.example.swapit.data.datasource.remote.interceptor.AuthInterceptor
import com.example.swapit.data.datasource.remote.interceptor.LoggingInterceptor
import com.example.swapit.data.datasource.remote.service.LoginService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

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
            Retrofit.Builder()
                .baseUrl((BuildConfig.SWAP_IT_BASE_URL))
                .client(client)
                .addConverterFactory(converterFactory)
                .build()

        loginServiceHolder.loginService = retrofit.create(LoginService::class.java)

        return retrofit
    }

    private fun okHttpClient(): OkHttpClient {
        val localLoginDataSource = LocalLoginDataSource(appContext)
        val authenticator = AuthAuthenticator(loginServiceHolder, LocalLoginDataSource(appContext))

        return OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor(localLoginDataSource))
            .authenticator(authenticator)
            .addInterceptor(LoggingInterceptor.create())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private fun jsonConverterFactory(json: Json): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }
}

class LoginServiceHolder {
    var loginService: LoginService? = null
}
