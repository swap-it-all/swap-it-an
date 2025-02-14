package com.example.swapit.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.ui.base.BaseViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginViewModel(
    application: Application,
    private val repository: SocialLoginRepository,
) : AndroidViewModel(application) {
    private val context = application.applicationContext

    /*private val _loginState = mutableStateOf<LoginState>(LoginState.None)
    val loginState: MutableState<LoginState> = _loginState*/
    val isLoggedIn = MutableStateFlow<Boolean>(false)

    fun kakaoLogin() {
        viewModelScope.launch {
            isLoggedIn.emit(isKakaoLoggedIn())
        }
    }

    fun kakaoLogout() {
        viewModelScope.launch {
            if (isKakaoLoggedOut()) {
                isLoggedIn.emit(false)
            }
        }
    }

    private suspend fun isKakaoLoggedOut(): Boolean =
        suspendCoroutine<Boolean> { continuation ->
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    continuation.resume(false)
                } else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    continuation.resume(true)
                }
            }
        }

    private suspend fun isKakaoLoggedIn(): Boolean =
        suspendCoroutine<Boolean> { continuation ->

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                    continuation.resume(false)
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    continuation.resume(true)
                }
            }
            val userApiClient = UserApiClient.instance

            if (userApiClient.isKakaoTalkLoginAvailable(context)) {
                userApiClient.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        userApiClient.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                userApiClient.loginWithKakaoAccount(context, callback = callback)
            }
        }

    companion object {
        private const val TAG = "LoginViewModel"
        fun factory(
            application: Application,
            repository: SocialLoginRepository,
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                LoginViewModel(
                    application = application,
                    repository = repository,
                )
            }
    }
}
