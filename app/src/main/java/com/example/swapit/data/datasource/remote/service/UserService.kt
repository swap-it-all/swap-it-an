package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import com.example.swapit.data.datasource.remote.dto.response.user.UserResponse
import retrofit2.http.GET

interface UserService {
    @GET("api/user/auth/info/my")
    suspend fun myUserInfo(): BaseResponse<UserResponse>
}
