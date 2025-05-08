package com.swapit.oopswap.data.datasource.local

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalLoginDataSource(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        withContext(Dispatchers.IO) {
            prefs.edit()
                .putString(ACCESS_TOKEN, accessToken)
                .putString(REFRESH_TOKEN, refreshToken)
                .commit()
        }
    }

    fun accessToken(): String? = prefs.getString(ACCESS_TOKEN, null)

    fun refreshToken(): String? = prefs.getString(REFRESH_TOKEN, null)

    fun clearTokens() {
        prefs.edit()
            .clear()
            .apply()
    }

    fun saveKakaoToken(kakaoToken: String) {
        prefs.edit()
            .putString("kakao_token", kakaoToken)
            .apply()
    }

    fun getKakaoToken(): String? = prefs.getString("kakao_token", null)

    companion object {
        const val PREFS_NAME = "swapit_auth"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }
}
