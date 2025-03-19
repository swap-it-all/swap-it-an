package com.example.swapit.data.datasource.remote.interceptor

import com.example.swapit.data.datasource.local.LocalLoginDataSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val localLoginDataSource: LocalLoginDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = localLoginDataSource.accessToken()

        val newRequest =
            request.newBuilder().apply {
                if (!isAuthExcluded(request)) {
                    accessToken?.let { addHeader("Authorization", "Bearer $it") }
                }
            }.build()

        return chain.proceed(newRequest)
    }

    private fun isAuthExcluded(request: Request): Boolean {
        val path = request.url.encodedPath
        return path.contains("/refresh") || path.contains("/logout")
    }
}
