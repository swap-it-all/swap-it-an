package com.example.swapit.ui.auth

import android.app.Activity
import android.app.Application
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.R
import com.example.swapit.domain.repository.SocialLoginRepository
import com.example.swapit.ui.navigation.BottomNavItem
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Shapes
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel,
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(BottomNavItem.Shopping.screenRoute){
                popUpTo(BottomNavItem.Login.screenRoute) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
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
        LoginButtons(
            navController = navController,
            viewModel = viewModel,
        )
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
fun LoginButtons(
    navController: NavHostController,
    viewModel: LoginViewModel,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        GoogleLoginButton()
        Spacer(modifier = Modifier.padding(10.dp))
        KakaoLoginButton(
            loginViewModel = viewModel,
        )
    }
}

@Composable
fun GoogleLoginButton() {
    val context = LocalContext.current
    val loginManager = remember { LoginManager(context as Activity) }
    val scope = rememberCoroutineScope()

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
        onClick = {
            scope.launch {
                val loginState = loginManager.googleLogin()
                when (loginState) {
                    is LoginState.Success -> {
                        Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                    }

                    is LoginState.Failure -> {
                        Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }

                    LoginState.None -> {}
                }
            }
        },
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
fun KakaoLoginButton(
    loginViewModel: LoginViewModel,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors =
        ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFEE500),
            contentColor = Black,
        ),
        contentPadding = PaddingValues(16.dp),
        shape = Shapes.small,
        onClick = { loginViewModel.kakaoLogin() },
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
fun LoginPreview() {
    LoginScreen(
        navController = rememberNavController(),
        viewModel = LoginViewModel(
            application = Application(),
            repository = SocialLoginRepository.instance(LocalContext.current),
        ),
    )
}
