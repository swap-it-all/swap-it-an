package com.example.swap_it.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swap_it.ui.component.ModalButton

class AuthActivity : ComponentActivity() {
    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KakaoLoginScreen(kakaoAuthViewModel)
        }
    }

    @Composable
    fun KakaoLoginScreen(viewModel: KakaoAuthViewModel) {
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

            Text(text = loginStatusInfoTitle, textAlign = TextAlign.Center, fontSize = 20.sp)
        }
    }
}
