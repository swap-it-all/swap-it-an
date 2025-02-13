package com.example.swap_it.data.datasource.remote.service

import com.example.swap_it.data.datasource.remote.dto.response.BaseResponse
import com.example.swap_it.data.datasource.remote.dto.response.login.SocialLoginResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("api/all/auth/login/kakao")
    suspend fun loginWithKakao(@Header("Authorization") token: String): BaseResponse<SocialLoginResponse>

    @POST("api/all/auth/login/google")
    suspend fun loginWithGoogle(@Header("Authorization") token: String): BaseResponse<SocialLoginResponse>
}
