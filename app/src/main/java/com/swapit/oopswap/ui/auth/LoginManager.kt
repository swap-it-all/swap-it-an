package com.swapit.oopswap.ui.auth

import android.app.Activity
import android.util.Log
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
                Log.e("Login", "Google ID token is null")
                return LoginState.Failure("Google 로그인 토큰을 가져오는데 실패했습니다.")
            }

            Log.i("Login", "Google 로그인 성공: $idToken")
            LoginState.Success(idToken)
        } catch (e: GetCredentialCancellationException) {
            Log.e("Login", "Google 로그인이 사용자에 의해 취소되었습니다", e)
            LoginState.Failure("로그인이 취소되었습니다. 다시 시도해주세요.")
        } catch (e: GetCredentialException) {
            Log.e("Login", "Google 로그인 실패: ${e.message}", e)
            when {
                e.message?.contains("no credential", ignoreCase = true) == true -> {
                    LoginState.Failure("사용 가능한 계정이 없습니다.")
                }
                else -> {
                    LoginState.Failure(e.message ?: "Google 로그인 실패")
                }
            }
        } catch (e: Exception) {
            Log.e("Login", "Google 로그인 중 예상치 못한 오류 발생: ${e.message}", e)
            LoginState.Failure(e.message ?: "Google 로그인 중 오류 발생")
        }
    }

    private fun hashSHA256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
