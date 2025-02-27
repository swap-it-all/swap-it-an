package com.example.swapit.data.datasource.remote.service

import com.example.swapit.data.datasource.remote.dto.response.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface UserService {
    @GET("api/user/auth/info/my")
    suspend fun myUserInfo(): BaseResponse<UserResponse>
}

@Serializable
data class UserResponse(
    @SerialName("usersId") val id: Long,
    val nickname: String,
    val profileImageUrl: String,
    val email: String,
    val totalGoodsCount: Long,
    val completedSwapCount: Long,
    val ratingAverage: Double,
    val reviews: List<ReviewResponse>,
)

@Serializable
data class ReviewResponse(
    val userId: Long,
    val profileImageUrl: String,
    val nickName: String,
    val rating: Double,
    val content: String,
    val createdAt: String,
)
