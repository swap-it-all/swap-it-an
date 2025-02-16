package com.example.swapit.data.datasource.local

import android.content.Context

class LocalSocialLoginDataSource(context: Context) {
    private val prefs = context.getSharedPreferences("swapit_auth", Context.MODE_PRIVATE)

    fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        prefs.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .apply()
    }

    fun accessToken(): String? = prefs.getString("access_token", null)

    fun refreshToken(): String? = prefs.getString("refresh_token", null)

    fun clearTokens() {
        prefs.edit()
            .clear()
            .apply()
    }
}
