package com.swapit.company.data.datasource.remote.interceptor

import android.util.Log
import com.swapit.company.data.datasource.local.LocalLoginDataSource
import com.swapit.company.data.datasource.remote.LoginServiceHolder
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val localLoginDataSource: LocalLoginDataSource,
    private val loginServiceHolder: LoginServiceHolder,
    private val onLogout: () -> Unit,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // 인증이 제외된 요청인지 확인
        if (isAuthExcluded(originalRequest)) {
            return chain.proceed(originalRequest)
        }

        // 액세스 토큰 추가
        val accessToken = runBlocking { localLoginDataSource.accessToken() }
        Log.d("accessToken", accessToken ?: "fail")
        val requestWithToken =
            originalRequest.newBuilder()
                .apply {
                    accessToken?.let {
                        header("Authorization", "Bearer $it")
                    }
                }
                .build()

        val initialResponse = chain.proceed(requestWithToken)

        // 토큰 만료 시 처리
        if (initialResponse.code == 401) {
            initialResponse.close() // 기존 응답 닫기

            val newAccessToken =
                refreshAccessToken() ?: run {
                    Log.e("refreshAccessToken", "fail")
                    onLogout()
                    throw Exception("Unable to refresh token. Logging out.")
                }
            Log.d("refreshAccessToken", newAccessToken)
            // 갱신된 토큰으로 새로운 요청 생성
            val newRequest =
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()

            return chain.proceed(newRequest) // 재요청
        }

        return initialResponse
    }

    private fun isAuthExcluded(request: Request): Boolean {
        val path = request.url.encodedPath
        return path.contains("/refresh") || path.contains("/logout")
    }

    private fun refreshAccessToken(): String? {
        val loginService = loginServiceHolder.loginService ?: return null
        val refreshToken = runBlocking { localLoginDataSource.refreshToken() } ?: return null

        return runBlocking {
            try {
                val results = loginService.refreshToken("Bearer $refreshToken").results
                localLoginDataSource.saveTokens(results.accessToken, results.refreshToken)
                Log.d("refreshAccessToken 저장", results.accessToken)
                results.accessToken
            } catch (e: Exception) {
                null // 갱신 실패 시 null 반환
            }
        }
    }
}
