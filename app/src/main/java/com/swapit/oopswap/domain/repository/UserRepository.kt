package com.swapit.oopswap.domain.repository

import android.content.Context
import android.net.Uri
import com.swapit.oopswap.data.datasource.RemoteUserDataSource
import com.swapit.oopswap.data.datasource.remote.ServiceModule
import com.swapit.oopswap.data.repository.DefaultUserRepository
import com.swapit.oopswap.domain.model.user.UserInfo

interface UserRepository {
    suspend fun myUserInfo(): Result<UserInfo>

    suspend fun updateNickname(nickname: String)

    suspend fun updateProfileImage(image: Uri)

    companion object {
        private var instance: UserRepository? = null

        fun instance(context: Context, onLogout: () -> Unit = {}): UserRepository {
            if (instance == null) {
                instance =
                    DefaultUserRepository(
                        remoteSource = RemoteUserDataSource(ServiceModule.userService),
                        context = context,
                        onLogout = onLogout
                    )
            }
            return instance!!
        }
    }
}
