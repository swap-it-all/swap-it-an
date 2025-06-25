package com.swapit.oopswap.data.datasource.remote.interceptor

import android.util.Log
import com.swapit.oopswap.data.auth.TokenStateManager
import com.swapit.oopswap.data.auth.TokenStateManager.isExpired
import com.swapit.oopswap.data.datasource.local.LocalLoginDataSource
import com.swapit.oopswap.data.datasource.remote.LoginServiceHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.time.delay
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.time.Duration

class AuthAuthenticator(
    private val loginServiceHolder: LoginServiceHolder,
    private val localLoginDataSource: LocalLoginDataSource,
    private val onLogout: () -> Unit,
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        val url = response.request.url.toString()
        Log.d("AuthAuthenticator", "🚨 Authenticator 작동: $url")
        
        // /withdraw 엔드포인트는 토큰 갱신 시도하지 않음
        if (url.contains("/withdraw")) {
            Log.d("AuthAuthenticator", "회원 탈퇴 API는 토큰 갱신 건너뜀")
            return null
        }
        
        if (responseCount(response) > 10) {
            Log.d("AuthAuthenticator", "최대 재시도 횟수 초과")
            return null
        }

        return runBlocking {
            try {
            val accessToken = getOrRefreshTokens()?.first ?: return@runBlocking null
            response.request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
            } catch (e: Exception) {
                Log.e("AuthAuthenticator", "토큰 갱신 중 오류 발생", e)
                if (!url.contains("/withdraw")) {
                    // 회원 탈퇴가 아닌 경우에만 토큰 초기화 및 로그아웃
                    onExpired()
                }
                null
            }
        }
    }

    private suspend fun getOrRefreshTokens(): Pair<String, String>? {
        return when (val state = TokenStateManager.tokenFlow.value) {
            is TokenStateManager.TokenState.Valid -> {
                val (access, refresh) = state.tokens
                if (isExpired(access)) {
                    // 만료된 토큰이면 강제로 refresh
                    refreshTokens()
                } else {
                    state.tokens
                }
            }

            is TokenStateManager.TokenState.Refreshing -> {
                TokenStateManager.tokenFlow
                    .first { it is TokenStateManager.TokenState.Valid }
                    .let { (it as TokenStateManager.TokenState.Valid).tokens }
            }

            else -> {
                mutex.withLock {
                    when (val newState = TokenStateManager.tokenFlow.value) {
                        is TokenStateManager.TokenState.Valid -> {
                            val (access, _) = newState.tokens
                            if (isExpired(access)) refreshTokens() else newState.tokens
                        }

                        else -> refreshTokens()
                    }
                }
            }
        }
    }

    private suspend fun refreshTokens(): Pair<String, String>? {
        val refreshToken = localLoginDataSource.refreshToken() ?: return null
        val loginService = loginServiceHolder.loginService ?: return null

        Log.d("AuthAuthenticator", "⚠️ refreshTokens() 호출됨")

        return try {
            TokenStateManager.tokenFlow.value = TokenStateManager.TokenState.Refreshing
            val result = loginService.refreshToken("Bearer $refreshToken").results

            localLoginDataSource.saveTokens(result.accessToken, result.refreshToken)
            delay(Duration.ofMillis(1000)) // 💡 delay 삽입

            val tokens = result.accessToken to result.refreshToken
            TokenStateManager.tokenFlow.value = TokenStateManager.TokenState.Valid(tokens)
            Log.d("AuthAuthenticator", "✅ 리프레시 성공, 새 토큰: $tokens")
            tokens
        } catch (e: Exception) {
            Log.e("AuthAuthenticator", "❌ 리프레시 실패: ${e.message}", e)
            throw e
        }
    }

    // 만료 처리 헬퍼
    private fun onExpired(): Nothing? {
        onLogout() // ← RetrofitModule 에서 전달한 콜백(로그아웃·네비게이트)
        TokenStateManager.tokenFlow.value = TokenStateManager.TokenState.Idle
        return null // 인증 실패로 후속 요청 차단
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
