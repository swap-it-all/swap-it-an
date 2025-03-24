package com.swapit.company.data.datasource.remote.service

import com.swapit.company.data.datasource.remote.dto.request.user.UserProfileRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.datasource.remote.dto.response.user.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface UserService {
    @GET("api/user/auth/info/my")
    suspend fun myUserInfo(): BaseResponse<UserResponse>

    @PATCH("api/user/auth/profile/nickname")
    suspend fun updateNickname(
        @Body nickname: UserProfileRequest,
    ): BaseResponse<Unit>

    @Multipart
    @PATCH("api/user/auth/profile/image")
    suspend fun updateProfileImage(
        @Part image: MultipartBody.Part,
    ): BaseResponse<Unit>

}
