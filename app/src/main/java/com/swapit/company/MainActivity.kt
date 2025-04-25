package com.swapit.company

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.swapit.company.data.datasource.remote.StompModule
import com.swapit.company.data.datasource.remote.createNotificationChannel
import com.swapit.company.domain.repository.AlertRepository
import com.swapit.company.domain.repository.ChatRepository
import com.swapit.company.domain.repository.LoginRepository
import com.swapit.company.ui.alert.AlertViewModel
import com.swapit.company.ui.auth.LoginManager
import com.swapit.company.ui.auth.LoginViewModel
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.navigation.NavigationModule

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
                        ),
                )

            // ✅ LifecycleObserver 추가 (앱이 종료될 때 WebSocket 해제)
            lifecycle.addObserver(
                LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_STOP) {
                        stompModule.disconnect()
                    }
                },
            )
            navigationModule.NavigationGraph(
                navController,
                loginViewModel,
                chatViewModel,
                alertViewModel,
                stompModule,
                application,
            )
        }
    }
}
