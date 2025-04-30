package com.swapit.oopswap.data.datasource.remote.service

import com.swapit.oopswap.data.datasource.remote.dto.request.login.WithDrawRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.dto.response.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @GET("api/all/auth/login/kakao")
    suspend fun loginWithKakao(
        @Header("Authorization") token: String,
    ): BaseResponse<LoginResponse>

    @GET("api/all/auth/login/google")
    suspend fun loginWithGoogle(
        @Header("Authorization") token: String,
    ): BaseResponse<LoginResponse>

    @POST("api/user/auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): BaseResponse<LoginResponse>

    @POST("api/user/auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String,
    ): BaseResponse<Unit>

    @POST("api/user/auth/withdraw/kakao")
    suspend fun deleteAccount(
        @Header("Authorization") authToken: String,
        @Header("X-Kakao-Token") kakaoToken: String,
        @Body reason: WithDrawRequest,
    ): BaseResponse<Unit>
}
