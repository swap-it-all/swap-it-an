package com.swapit.oopswap.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.swapit.oopswap.domain.repository.LoginRepository
import com.swapit.oopswap.ui.base.BaseViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginViewModel(
    application: Application,
    private val repository: LoginRepository,
    private val loginManager: LoginManager,
) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        _isLoggedIn.value = repository.accessToken() != null
        // 자동 로그인 시 로그인 타입 복원
        if (_isLoggedIn.value) {
            val savedLoginType = loginManager.currentLoginType
            if (savedLoginType != null) {
                loginManager.setLoginType(savedLoginType)
            } else {
                Log.w(TAG, "저장된 로그인 타입이 없음")
            }
        }
    }

    fun googleLogin() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = loginManager.googleLogin()
                when (result) {
                    is LoginState.Success -> {
                        repository.loginWithGoogle(result.token)
                        _isLoggedIn.emit(true)
                    }
                    is LoginState.Failure -> {
                        Log.e(TAG, "Google 로그인 실패: ${result.message}")
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                Log.e(TAG, "Google 로그인 중 예외 발생", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun kakaoLogin() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val kakaoToken = isKakaoLoggedIn().getOrNull()
                if (kakaoToken != null) {
                    repository.saveKakaoToken(kakaoToken)
                    repository.loginWithKakao(kakaoToken)
                    loginManager.setLoginType(LoginManager.LoginType.KAKAO)
                    _isLoggedIn.emit(true)
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "로그아웃 시작")
                
                // 구글 로그아웃 시도
                if (loginManager.logout()) {
                    repository.logout(repository.refreshToken() ?: "")
                    _isLoggedIn.emit(false)
                    return@launch
                } else {
                    Log.d(TAG, "구글 로그아웃 실패 또는 구글 로그인 상태가 아님")
                }

                // 카카오 로그아웃 시도
                val kakaoToken = repository.getKakaoToken()
                if (kakaoToken != null) {
                    if (isKakaoLoggedOut()) {
                        repository.logout(repository.refreshToken() ?: "")
                        _isLoggedIn.emit(false)
                    } else {
                        Log.d(TAG, "카카오 로그아웃 실패")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "로그아웃 중 오류 발생", e)
            }
        }
    }

    fun deleteAccount(
        reason: String,
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            Log.d(TAG, "deleteAccount() called with: reason = $reason") // ✅ 실행 확인용

            val authToken = repository.accessToken()
            val kakaoToken = repository.getKakaoToken()
            Log.d(TAG, "Auth Tokens -> authToken: $authToken, kakaoToken: $kakaoToken") // ✅ 토큰 확인

            if (authToken != null && kakaoToken != null) {
                val result = repository.deleteAccount(authToken, kakaoToken, reason)
                Log.d(TAG, "deleteAccount() result: $result") // ✅ API 호출 결과 확인

                if (result) {
                    _isLoggedIn.emit(false)
                    onSuccess()
                }
            } else {
                Log.e(TAG, "토큰이 없습니다: authToken=$authToken, kakaoToken=$kakaoToken")
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

    companion object {
        private const val TAG = "LoginViewModel"

        fun factory(
            application: Application,
            repository: LoginRepository,
            loginManager: LoginManager,
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                LoginViewModel(
                    application = application,
                    repository = repository,
                    loginManager = loginManager,
                )
            }
    }
}
