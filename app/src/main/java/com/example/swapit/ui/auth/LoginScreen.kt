package com.example.swapit.ui.auth

import android.app.Activity
import android.app.Application
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
import com.example.swapit.domain.repository.LoginRepository
import com.example.swapit.ui.navigation.NavItem
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Shapes
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel,
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(NavItem.Shopping.screenRoute) {
                popUpTo(NavItem.Login.screenRoute) { inclusive = true }
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
fun LoginButtons(viewModel: LoginViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        GoogleLoginButton(
            viewModel = viewModel,
        )
        Spacer(modifier = Modifier.padding(10.dp))
        KakaoLoginButton(
            viewModel = viewModel,
        )
    }
}

@Composable
fun GoogleLoginButton(viewModel: LoginViewModel) {
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
        onClick = { viewModel.googleLogin() },
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
fun KakaoLoginButton(viewModel: LoginViewModel) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFEE500),
                contentColor = Black,
            ),
        contentPadding = PaddingValues(16.dp),
        shape = Shapes.small,
        onClick = { viewModel.kakaoLogin() },
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
    val context = LocalContext.current
    LoginScreen(
        navController = rememberNavController(),
        viewModel =
            LoginViewModel(
                application = Application(),
                repository = LoginRepository.instance(context),
                loginManager = LoginManager(context as Activity),
            ),
    )
}
