package com.swapit.oopswap.data.auth

import com.swapit.oopswap.data.datasource.local.LocalLoginDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject
import java.util.Base64

object TokenStateManager {
    val tokenFlow = MutableStateFlow<TokenState>(TokenState.Idle)

    sealed class TokenState {
        object Idle : TokenState()
        object Refreshing : TokenState()
        data class Valid(val tokens: Pair<String, String>) : TokenState()
    }

    // TokenStateManager.kt
    fun initializeFromLocal(localLoginDataSource: LocalLoginDataSource) {
        val accessToken = localLoginDataSource.accessToken()
        val refreshToken = localLoginDataSource.refreshToken()

        if (!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank() && !isExpired(accessToken)) {
            tokenFlow.value = TokenState.Valid(accessToken to refreshToken)
        } else {
            tokenFlow.value = TokenState.Idle
        }
    }

    fun isExpired(accessToken: String): Boolean {
        // payload(Base64Url) 디코딩 → JSON 파싱 → exp(claim) 꺼내기
        val payload = accessToken.split('.')[1]
        val json = String(Base64.getUrlDecoder().decode(payload))
        val exp = JSONObject(json).getLong("exp")
        val now = System.currentTimeMillis() / 1000
        return now >= exp
    }
}