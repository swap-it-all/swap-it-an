package com.swapit.company.ui.user

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swapit.company.domain.model.user.UserInfo
import com.swapit.company.domain.model.user.UserSwapStats
import com.swapit.company.domain.repository.UserRepository
import com.swapit.company.ui.base.BaseViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val repository: UserRepository,
) : ViewModel() {
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
        viewModelScope.launch {
            _userInfo.value = repository.myUserInfo()
        }
    }

    fun updateProfileImage(image: Uri) {
        viewModelScope.launch {
            _userInfo.value = _userInfo.value.copy(profileImageUrl = image.toString())
            isProfileImageUpdated = true
        }
    }

    fun updateNickname(nickname: String) {
        viewModelScope.launch {
            _userInfo.value = _userInfo.value.copy(nickname = nickname)
        }
    }

    fun saveUserInfo() {
        viewModelScope.launch {
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
