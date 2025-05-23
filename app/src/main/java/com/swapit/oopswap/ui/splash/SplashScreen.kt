package com.swapit.oopswap.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.swapit.oopswap.R
import com.swapit.oopswap.ui.auth.LoginViewModel
import com.swapit.oopswap.ui.navigation.NavItem
import com.swapit.oopswap.ui.theme.Primary
import com.swapit.oopswap.ui.theme.White

@Composable
fun SplashScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
) {
    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()

    val alpha =
        remember {
            Animatable(0f)
        }

    LaunchedEffect(isLoggedIn) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(1000),
        )

        // 2. 애니메이션 끝나자마자 바로 이동
        val targetRoute =
            if (isLoggedIn) {
                NavItem.Shopping.screenRoute
            } else {
                NavItem.Login.screenRoute
            }

        navController.navigate(targetRoute) {
            popUpTo(NavItem.Splash.screenRoute) { inclusive = true }
        }

       /* Handler(Looper.getMainLooper()).postDelayed({
            when (isLoggedIn) {
                true -> {
                    navController.navigate(NavItem.Shopping.screenRoute) {
                        popUpTo(NavItem.Splash.screenRoute) { inclusive = true }
                    }
                }

                false -> {
                    navController.navigate(NavItem.Login.screenRoute) {
                        popUpTo(NavItem.Splash.screenRoute) { inclusive = true }
                    }
                }
            }
        }, 1000L)*/
    }
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Primary)
                .alpha(alpha.value),
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        Icon(
            painter =
                painterResource(
                    id = R.drawable.ic_logo,
                ),
            contentDescription = "swapit logo",
            tint = White,
            modifier =
                Modifier
                    .size(112.dp)
                    .alpha(alpha.value),
        )
    }
}
