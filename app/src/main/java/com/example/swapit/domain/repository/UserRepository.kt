package com.example.swapit.domain.repository

import com.example.swapit.data.datasource.RemoteUserDataSource
import com.example.swapit.data.datasource.remote.ServiceModule
import com.example.swapit.data.repository.DefaultUserRepository
import com.example.swapit.domain.model.user.UserInfo

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
