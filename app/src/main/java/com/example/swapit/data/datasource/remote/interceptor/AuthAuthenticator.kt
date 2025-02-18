package com.example.swapit.data.datasource.remote.interceptor

import com.example.swapit.data.datasource.local.LocalLoginDataSource
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator(private val localLoginDataSource: LocalLoginDataSource) : Authenticator {
    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        if (responseCount(response) >= 2) {
            // todo logout
            return null
        }
        val refreshToken = localLoginDataSource.refreshToken() ?: return null

        return newRequestWithAccessToken(refreshToken, response.request)
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
