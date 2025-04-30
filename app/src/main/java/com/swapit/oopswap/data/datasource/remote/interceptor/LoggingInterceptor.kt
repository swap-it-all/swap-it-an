package com.swapit.oopswap.data.datasource.remote.interceptor

import com.swapit.oopswap.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {
    fun create(): Interceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )
}
