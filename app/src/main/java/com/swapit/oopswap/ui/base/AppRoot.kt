package com.swapit.oopswap.ui.base

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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

@Composable
fun AppRoot(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    chatViewModel: ChatViewModel,
    alertViewModel: AlertViewModel,
    stompModule: StompModule,
    application: Application,
) {
    val message by AlertNotifier.messageFlow.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // 알림 메시지
        TopToastMessage(
            message = message ?: "",
            isVisible = message != null,
            onDismiss = { AlertNotifier.clear() },
        )

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
