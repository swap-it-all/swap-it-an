package com.example.swapit.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.domain.repository.SocialLoginRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginViewModel(
    application: Application,
    private val repository: SocialLoginRepository,
) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    init {
        _isLoggedIn.value = repository.accessToken() != null
    }

    fun kakaoLogin() {
        viewModelScope.launch {
            val accessToken = isKakaoLoggedIn().getOrNull()
            accessToken?.let {
                repository.loginWithKakao(it)
                _isLoggedIn.emit(true)
            }
        }
    }

    fun kakaoLogout() {
        viewModelScope.launch {
            if (isKakaoLoggedOut()) {
                repository.logout(repository.refreshToken()!!)
                _isLoggedIn.emit(false)
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

    private suspend fun isKakaoLoggedIn(): Result<String> =
        suspendCoroutine<Result<String>> { continuation ->

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                    continuation.resume(Result.failure(error))
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    continuation.resume(Result.success(token.accessToken))
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


    fun sendAccessToken(accessToken: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val data = repository.loginWithKakao(accessToken)
                Log.i("카카오 로그인", "서버로부터 받은 데이터: ${data.key}")
                Log.i("카카오 로그인", "서버로부터 받은 데이터: ${data.accessToken}")
                Log.i("카카오 로그인", "서버로부터 받은 데이터: ${data.refreshToken}")
            } catch (e: Exception) {
                Log.e("카카오 로그인", "JWT 요청 실패: ${e.message}")
            }
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
