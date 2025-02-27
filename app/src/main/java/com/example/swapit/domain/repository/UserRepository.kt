package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteUserDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.datasource.remote.service.ReviewResponse
import com.example.swapit.data.datasource.remote.service.UserResponse

interface UserRepository {
    suspend fun myUserInfo(): UserInfo

    companion object {
        private var instance: UserRepository? = null

        fun instance(): UserRepository {
            if (instance == null) {
                instance = DefaultUserRepository(
                    remoteSource = RemoteUserDataSource(ServiceModule.userService),
                )
            }
            return instance!!
        }
    }
}

class DefaultUserRepository(
    private val remoteSource: RemoteUserDataSource,
) : UserRepository {
    override suspend fun myUserInfo(): UserInfo {
        return remoteSource.myUserInfo().toDomain()
    }
}

fun UserResponse.toDomain(): UserInfo {
    return UserInfo(
        id = this.id,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        email = this.email,
        totalGoodsCount = this.totalGoodsCount,
        completedSwapCount = this.completedSwapCount,
        ratingAverage = this.ratingAverage,
        reviews = this.reviews.map {
            Review(
                userId = it.userId,
                profileImageUrl = it.profileImageUrl,
                nickName = it.nickName,
                rating = it.rating,
                content = it.content,
                createdAt = it.createdAt,
            )
        }
    )
}

fun ReviewResponse.toDomain(): Review {
    return Review(
        userId = this.userId,
        profileImageUrl = this.profileImageUrl,
        nickName = this.nickName,
        rating = this.rating,
        content = this.content,
        createdAt = this.createdAt,
    )
}

data class UserInfo(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String,
    val email: String,
    val totalGoodsCount: Long,
    val completedSwapCount: Long,
    val ratingAverage: Double,
    val reviews: List<Review>,
)

data class UserSwapStats(
    val totalGoodsCount: Long,
    val completedSwapCount: Long,
    val ratingAverage: Double,
)

data class Review(
    val userId: Long,
    val profileImageUrl: String,
    val nickName: String,
    val rating: Double,
    val content: String,
    val createdAt: String,
)
