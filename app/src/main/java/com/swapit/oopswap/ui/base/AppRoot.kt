package com.swapit.oopswap.ui.base

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.swapit.oopswap.data.datasource.remote.StompModule
import com.swapit.oopswap.ui.alert.AlertViewModel
import com.swapit.oopswap.ui.auth.LoginViewModel
import com.swapit.oopswap.ui.chat.ChatViewModel
import com.swapit.oopswap.ui.component.TopToastMessage
import com.swapit.oopswap.ui.navigation.NavigationModule
import toIconResId

@Composable
fun AppRoot(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    chatViewModel: ChatViewModel,
    alertViewModel: AlertViewModel,
    stompModule: StompModule,
    application: Application,
) {
    val alert by AlertNotifier.alertState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // 상단 알림 표시
        alert?.let { (message, type) ->
            TopToastMessage(
                message = message,
                iconResId = type.toIconResId(),
                isVisible = true,
                onDismiss = { AlertNotifier.clear() },
            )

            // 자동 사라짐 기능 추가: 5초 후 알림 닫기
            LaunchedEffect(alert) {
                kotlinx.coroutines.delay(2000) // 밀리초 단위 (5초 → 필요 시 늘리세요)
                AlertNotifier.clear()
            }
        }

        NavigationModule().NavigationGraph(
            navController,
            loginViewModel,
            chatViewModel,
            alertViewModel,
            stompModule,
            application,
        )
    }
}
