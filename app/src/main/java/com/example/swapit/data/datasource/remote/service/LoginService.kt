package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.login.SocialLoginResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @GET("api/all/auth/login/kakao")
    suspend fun loginWithKakao(
        @Header("Authorization") token: String,
    ): BaseResponse<SocialLoginResponse>

    @GET("api/all/auth/login/google")
    suspend fun loginWithGoogle(
        @Header("Authorization") token: String,
    ): BaseResponse<SocialLoginResponse>

    @POST("api/user/auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): BaseResponse<SocialLoginResponse>

    @POST("api/user/auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String,
    ): BaseResponse<Unit>
}
