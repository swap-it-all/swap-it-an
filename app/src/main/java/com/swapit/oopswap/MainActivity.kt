package com.swapit.oopswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.swapit.oopswap.data.datasource.remote.StompModule
import com.swapit.oopswap.data.datasource.remote.createNotificationChannel
import com.swapit.oopswap.data.datasource.remote.dto.response.ErrorResponse
import com.swapit.oopswap.domain.repository.AlertRepository
import com.swapit.oopswap.domain.repository.ChatRepository
import com.swapit.oopswap.domain.repository.LoginRepository
import com.swapit.oopswap.ui.alert.AlertViewModel
import com.swapit.oopswap.ui.auth.LoginManager
import com.swapit.oopswap.ui.auth.LoginViewModel
import com.swapit.oopswap.ui.base.GlobalEventBus
import com.swapit.oopswap.ui.base.UiEvent
import com.swapit.oopswap.ui.base.AppRoot
import com.swapit.oopswap.ui.chat.ChatViewModel
import com.swapit.oopswap.ui.component.ErrorDialog
import com.swapit.oopswap.ui.navigation.NavigationModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            createNotificationChannel(this)
            val navController = rememberNavController()
            val navigationModule = NavigationModule()
            val loginViewModel: LoginViewModel =
                viewModel(
                    factory =
                        LoginViewModel.factory(
                            application,
                            LoginRepository.instance(this),
                            LoginManager(this),
                        ),
                )
            val stompModule = StompModule(LoginRepository.instance(this), application)

            val chatViewModel: ChatViewModel =
                viewModel(
                    factory =
                        ChatViewModel.factory(
                            repository = ChatRepository.instance(),
                            stompModule,
                        ),
                )
            val alertViewModel: AlertViewModel =
                viewModel(
                    factory =
                        AlertViewModel.factory(
                            repository = AlertRepository.instance(),
                            stompModule,
                            application = application,
                        ),
                )

            val errorResponse = remember { mutableStateOf<ErrorResponse?>(null) }

            LaunchedEffect(Unit) {
                GlobalEventBus.events.collect { event ->
                    if (event is UiEvent.ShowError) {
                        errorResponse.value = event.error
                    }
                }
            }

            errorResponse.value?.let { err ->
                ErrorDialog(
                    error = err,
                    onDismiss = { errorResponse.value = null },
                )
            }

            AppRoot(
                navController = navController,
                loginViewModel = loginViewModel,
                chatViewModel = chatViewModel,
                alertViewModel = alertViewModel,
                stompModule = stompModule,
                application = application,
            )
        }
    }
}
