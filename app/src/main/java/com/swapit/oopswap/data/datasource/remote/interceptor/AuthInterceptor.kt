package com.swapit.oopswap.data.datasource.remote.interceptor

import com.swapit.oopswap.data.datasource.local.LocalLoginDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val localLoginDataSource: LocalLoginDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // 인증이 제외된 요청인지 확인
        if (isAuthExcluded(originalRequest)) {
            return chain.proceed(originalRequest)
        }

        // 액세스 토큰 추가
        val accessToken = runBlocking { localLoginDataSource.accessToken() }
        val requestWithToken =
            originalRequest.newBuilder()
                .apply {
                    accessToken?.let {
                        header("Authorization", "Bearer $it")
                    }
                }
                .build()

        return chain.proceed(requestWithToken)
    }

    private fun isAuthExcluded(request: Request): Boolean {
        val path = request.url.encodedPath
        return path.contains("/refresh") || path.contains("/logout")
    }
}
