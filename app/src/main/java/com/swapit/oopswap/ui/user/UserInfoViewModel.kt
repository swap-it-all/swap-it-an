package com.swapit.oopswap.ui.user

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelProvider
import com.swapit.oopswap.domain.model.user.UserInfo
import com.swapit.oopswap.domain.model.user.UserSwapStats
import com.swapit.oopswap.domain.repository.UserRepository
import com.swapit.oopswap.ui.base.BaseViewModel
import com.swapit.oopswap.ui.base.BaseViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserInfoViewModel(
    private val repository: UserRepository,
) : BaseViewModel() {
    val expanded = mutableStateOf(false)
    val selectedText = mutableStateOf("선택해주세요.")
    val etcText = mutableStateOf("")
    private var _userInfo: MutableStateFlow<UserInfo> =
        MutableStateFlow(
            UserInfo(
                id = 0,
                nickname = "",
                profileImageUrl = "",
                email = "",
                swapStats =
                    UserSwapStats(
                        totalGoodsCount = 0,
                        completedSwapCount = 0,
                        ratingAverage = 0.0,
                    ),
                reviews = emptyList(),
            ),
        )
    val userInfo: StateFlow<UserInfo?> = _userInfo.asStateFlow()

    private var _saveCompleted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val saveCompleted: StateFlow<Boolean> = _saveCompleted.asStateFlow()

    private var isProfileImageUpdated = false

    fun myUserInfo() {
        safeLaunch {
            _userInfo.value = repository.myUserInfo()
        }
    }

    fun updateProfileImage(image: Uri) {
        safeLaunch {
            _userInfo.value = _userInfo.value.copy(profileImageUrl = image.toString())
            isProfileImageUpdated = true
        }
    }

    fun updateNickname(nickname: String) {
        safeLaunch {
            _userInfo.value = _userInfo.value.copy(nickname = nickname)
        }
    }

    fun saveUserInfo() {
        safeLaunch {
            if (isProfileImageUpdated) {
                repository.updateProfileImage(Uri.parse(_userInfo.value.profileImageUrl))
                isProfileImageUpdated = false
            }
            repository.updateNickname(_userInfo.value.nickname)
            _saveCompleted.value = true
        }
    }

    fun resetSaveCompleted() {
        _saveCompleted.value = false
    }

    companion object {
        fun factory(repository: UserRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                UserInfoViewModel(repository = repository)
            }
    }
}
