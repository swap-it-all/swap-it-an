package com.swapit.oopswap.ui.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.swapit.oopswap.R
import java.security.MessageDigest
import java.util.UUID

class LoginManager(
    private val activity: Activity,
) {
    private val credentialManager = CredentialManager.create(activity)
    private val prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    var currentLoginType: LoginType?
        get() {
            val type = prefs.getString(KEY_LOGIN_TYPE, null)?.let { LoginType.valueOf(it) }
            return type
        }
        set(value) {
            prefs.edit().apply {
                if (value != null) {
                    putString(KEY_LOGIN_TYPE, value.name)
                } else {
                    remove(KEY_LOGIN_TYPE)
                }
                apply()
            }
            val savedType = prefs.getString(KEY_LOGIN_TYPE, null)
        }

    init {
        Log.d(TAG, "LoginManager 초기화, 현재 로그인 타입: $currentLoginType")
    }

    suspend fun googleLogin(): LoginState {
        return try {
            val rawNonce = UUID.randomUUID().toString()
            val hashedNonce = hashSHA256(rawNonce)

            val googleIdOption =
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(activity.getString(R.string.google_web_client_id))
                    .setNonce(hashedNonce)
                    .build()

            val request =
                GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

            val result =
                credentialManager.getCredential(
                    request = request,
                    context = activity,
                )

            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken

            if (idToken == null) {
                return LoginState.Failure("Google 로그인 토큰을 가져오는데 실패했습니다.")
            }

            setLoginType(LoginType.GOOGLE)

            LoginState.Success(idToken)
        } catch (e: GetCredentialCancellationException) {
            LoginState.Failure("로그인이 취소되었습니다. 다시 시도해주세요.")
        } catch (e: GetCredentialException) {
            when {
                e.message?.contains("no credential", ignoreCase = true) == true -> {
                    LoginState.Failure("사용 가능한 계정이 없습니다.")
                }
                else -> {
                    LoginState.Failure(e.message ?: "Google 로그인 실패")
                }
            }
        } catch (e: Exception) {
            LoginState.Failure(e.message ?: "Google 로그인 중 오류 발생")
        }
    }

    suspend fun logout(): Boolean {
        return try {
            when (currentLoginType) {
                LoginType.GOOGLE -> {
                    val request = ClearCredentialStateRequest()
                    credentialManager.clearCredentialState(request)
                    setLoginType(null)
                    true
                }
                else -> {
                    Log.d(TAG, "구글 로그인 상태가 아님")
                    false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "로그아웃 중 오류 발생", e)
            false
        }
    }

    fun setLoginType(type: LoginType?) {
        currentLoginType = type
    }

    private fun hashSHA256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    enum class LoginType {
        GOOGLE,
        KAKAO
    }

    companion object {
        private const val TAG = "LoginManager"
        private const val PREFS_NAME = "login_manager_prefs"
        private const val KEY_LOGIN_TYPE = "login_type"
    }
}
