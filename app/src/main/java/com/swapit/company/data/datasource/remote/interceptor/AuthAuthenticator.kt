package com.swapit.company.data.datasource.remote.interceptor

import com.swapit.company.data.datasource.local.LocalLoginDataSource
import com.swapit.company.data.datasource.remote.LoginServiceHolder
import com.swapit.company.data.datasource.remote.service.LoginService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator(
    private val loginServiceHolder: LoginServiceHolder,
    private val localLoginDataSource: LocalLoginDataSource,
) : Authenticator {

    private val mutex = Mutex() // 갱신 동기화를 위한 Mutex
    private val tokenFlow = MutableStateFlow<TokenState>(TokenState.Idle) // 토큰 상태 관리

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) {
            return null // 무한 루프 방지
        }

        // 토큰 갱신 상태를 체크 및 갱신 후 새 요청 빌드
        return runBlocking {
            val accessToken = getOrRefreshTokens()?.first ?: return@runBlocking null
            response.request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        }
    }

    private suspend fun getOrRefreshTokens(): Pair<String, String>? {
        return when (val state = tokenFlow.value) {
            is TokenState.Refreshing -> {
                // 갱신 중이라면 완료될 때까지 대기
                tokenFlow.first { it is TokenState.Valid }.let {
                    (it as? TokenState.Valid)?.tokens
                }
            }
            else -> {
                mutex.withLock {
                    // 갱신 상태 확인 후 다시 처리
                    if (tokenFlow.value is TokenState.Valid) {
                        (tokenFlow.value as TokenState.Valid).tokens
                    } else {
                        refreshTokens()
                    }
                }
            }
        }
    }

    private suspend fun refreshTokens(): Pair<String, String>? {
        val loginService = loginServiceHolder.loginService ?: return null
        val refreshToken = localLoginDataSource.refreshToken() ?: return null

        return try {
            tokenFlow.value = TokenState.Refreshing
            val results = loginService.refreshToken("Bearer $refreshToken").results
            val newAccessToken = results.accessToken
            val newRefreshToken = results.refreshToken

            localLoginDataSource.saveTokens(newAccessToken, newRefreshToken)
            val tokens = newAccessToken to newRefreshToken
            tokenFlow.value = TokenState.Valid(tokens)
            tokens
        } catch (e: Exception) {
            tokenFlow.value = TokenState.Idle
            null
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prevResponse = response.priorResponse
        while (prevResponse != null) {
            count++
            prevResponse = prevResponse.priorResponse
        }
        return count
    }

    private sealed class TokenState {
        object Idle : TokenState() // 초기 상태
        object Refreshing : TokenState() // 갱신 중
        data class Valid(val tokens: Pair<String, String>) : TokenState() // 유효한 토큰 상태
    }
}


