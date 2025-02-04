package com.example.swap_it.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.R
import com.example.swap_it.ui.theme.Black
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.Shapes
import com.example.swap_it.ui.theme.Typography
import com.example.swap_it.ui.theme.White

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }

    @Composable
    fun LoginScreen() {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = Paddings.xlarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GreetingSwapIt()
            Spacer(modifier = Modifier.padding(100.dp))
            LoginButtons()
        }
    }

    @Composable
    fun GreetingSwapIt() {
        Icon(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(70.dp, 112.dp),
            tint = Color.Unspecified,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "SWAPIT에 오신 것을", style = Typography.titleLarge)
            Text(text = "환영해요 :D", style = Typography.titleLarge)
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "돈 대신 물건으로!", style = Typography.bodyMedium, color = Gray3)
            Text(text = "필요 없는 물건에 새 생명을 불어넣어요.", style = Typography.bodyMedium, color = Gray3)
        }
    }

    @Composable
    fun LoginButtons() {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            GoogleLoginButton()
            Spacer(modifier = Modifier.padding(10.dp))
            KakaoLoginButton()
        }
    }

    @Composable
    fun GoogleLoginButton() {
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Black,
                ),
            contentPadding = PaddingValues(16.dp),
            shape = Shapes.small,
            border = BorderStroke(1.dp, Gray4),
            onClick = { Log.d("GoogleLoginButton", "GoogleLoginButton Clicked") },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "구글로 로그인하기",
                style = Typography.titleMedium,
            )
        }
    }

    @Composable
    fun KakaoLoginButton() {
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFEE500),
                    contentColor = Black,
                ),
            contentPadding = PaddingValues(16.dp),
            shape = Shapes.small,
            onClick = { Log.d("KakaoLoginButton", "KakaoLoginButton Clicked") },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_kakao),
                contentDescription = "Kakao",
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "카카오로 로그인하기", style = Typography.titleMedium)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        LoginScreen()
    }
}
