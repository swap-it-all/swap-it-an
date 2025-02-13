package com.example.swapit.ui.auth

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.swapit.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
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
                    .setServerClientId(activity.getString(R.string.google_client_id))
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

            Log.i("Login", "Google 로그인 성공: $idToken")
            LoginState.Success(idToken)
        } catch (e: GetCredentialException) {
            Log.e("Login", "Google 로그인 실패", e)
            LoginState.Failure(e.message ?: "Google 로그인 실패")
        } catch (e: Exception) {
            Log.e("Login", "Google 로그인 중 오류 발생", e)
            LoginState.Failure(e.message ?: "Google 로그인 중 오류 발생")
        }
    }

    private fun hashSHA256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
