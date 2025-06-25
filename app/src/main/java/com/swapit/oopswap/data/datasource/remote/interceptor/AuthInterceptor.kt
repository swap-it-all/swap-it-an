package com.swapit.oopswap.data.datasource.remote.interceptor

import com.swapit.oopswap.data.auth.TokenStateManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        // /refresh, /logout, /withdraw 는 제외
        if (path.contains("/refresh") || 
            path.contains("/logout") || 
            path.contains("/withdraw")) {
            return chain.proceed(request)
        }

        val token =
            runBlocking {
                // 3초 동안 기다렸다가 없으면 null
                withTimeoutOrNull(3000) {
                    TokenStateManager.tokenFlow.first { it is TokenStateManager.TokenState.Valid }
                }?.let { (it as? TokenStateManager.TokenState.Valid)?.tokens?.first }
                    // fallback: 즉시 value에서 가져오기 (last-chance)
                    ?: (TokenStateManager.tokenFlow.value as? TokenStateManager.TokenState.Valid)?.tokens?.first
            }

        val requestWithAuth =
            token?.let {
                request.newBuilder()
                    .header("Authorization", "Bearer $it")
                    .build()
            } ?: request

        return chain.proceed(requestWithAuth)
    }
}
