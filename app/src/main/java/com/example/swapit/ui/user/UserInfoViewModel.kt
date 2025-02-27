package com.example.swapit.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.domain.model.user.UserInfo
import com.example.swapit.domain.model.user.UserSwapStats
import com.example.swapit.domain.repository.UserRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private var _userInfo: MutableStateFlow<UserInfo> = MutableStateFlow(
        UserInfo(
            id = 0,
            nickname = "",
            profileImageUrl = "",
            email = "",
            swapStats = UserSwapStats(
                totalGoodsCount = 0,
                completedSwapCount = 0,
                ratingAverage = 0.0
            ),
            reviews = emptyList()
        )
    )
    val userInfo: StateFlow<UserInfo?> = _userInfo.asStateFlow()

    fun myUserInfo() {
        viewModelScope.launch {
            _userInfo.value = repository.myUserInfo()
        }
    }

    companion object {
        fun factory(repository: UserRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                UserInfoViewModel(repository = repository)
            }
    }
}
