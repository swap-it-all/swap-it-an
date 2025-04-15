package com.swapit.company.data.datasource.remote.interceptor

import com.swapit.company.data.datasource.local.LocalLoginDataSource
import com.swapit.company.data.datasource.remote.LoginServiceHolder
import com.swapit.company.data.datasource.remote.service.LoginService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator(
    private val loginServiceHolder: LoginServiceHolder,
    private val localLoginDataSource: LocalLoginDataSource,
) : Authenticator {
    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        val loginService = loginServiceHolder.loginService ?: return null
        if (responseCount(response) >= 2) {
            return null
        }

        val refreshToken =
            runBlocking {
                localLoginDataSource.refreshToken() ?: run {
                    loginService.refreshToken("Bearer ${localLoginDataSource.refreshToken()}").results.refreshToken
                }
            }

        val newTokens =
            newTokens(refreshToken, loginService) ?: run {
                return null
            }

        saveTokens(newTokens.first, newTokens.second)

        return newRequestWithAccessToken(newTokens.first, response.request)
    }

    private fun newTokens(
        refreshToken: String,
        loginService: LoginService,
    ): Pair<String, String>? {
        return runBlocking {
            try {
                val results = loginService.refreshToken("Bearer $refreshToken").results
                results.accessToken to results.refreshToken
            } catch (e: Exception) {
                null // 에러 발생 시 null 반환
            }
        }
    }

    private fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        runBlocking {
            localLoginDataSource.saveTokens(accessToken, refreshToken)
        }
    }

    private fun newRequestWithAccessToken(
        token: String,
        request: Request,
    ): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

    private fun responseCount(response: Response): Int {
        var count = 1
        var prevResponse = response.priorResponse
        while (prevResponse != null) {
            count++
            prevResponse = prevResponse.priorResponse
        }
        return count
    }
}
