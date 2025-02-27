package com.example.swapit.data.repository

import com.example.swapit.data.datasource.RemoteUserDataSource
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.user.UserInfo
import com.example.swapit.domain.repository.UserRepository

class DefaultUserRepository(
    private val remoteSource: RemoteUserDataSource,
) : UserRepository {
    override suspend fun myUserInfo(): UserInfo {
        return remoteSource.myUserInfo().toDomain()
    }
}
