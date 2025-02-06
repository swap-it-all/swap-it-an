package com.example.swap_it.ui.auth

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swap_it.ui.component.ModalButton
import kotlinx.coroutines.launch

class AuthActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KakaoLoginScreen(loginViewModel)
        }
    }

    @Composable
    fun KakaoLoginScreen(viewModel: LoginViewModel) {
        val isLoggedIn = viewModel.isLoggedIn.collectAsState()
        val loginStatusInfoTitle = if (isLoggedIn.value) "로그인 성공" else "로그인 실패"
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            ModalButton("카카오 로그인") {
                viewModel.kakaoLogin()
            }
            ModalButton("로그아웃") {
                viewModel.kakaoLogout()
            }

            GoogleSignScreen()
            Text(text = loginStatusInfoTitle, textAlign = TextAlign.Center, fontSize = 20.sp)
        }
    }
}

@Composable
fun GoogleSignScreen() {
    val activity = LocalContext.current
    val loginManager = remember { LoginManager(activity as Activity) }
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Button(onClick = {
            scope.launch {
                val loginState = loginManager.googleLogin()
                when (loginState) {
                    is LoginState.Success -> {
                        Toast.makeText(activity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    }
                    is LoginState.Failure -> {
                        Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }

                    LoginState.None -> TODO()
                }
            }
        }) {
            Text("구글 로그인")
        }
    }
}

