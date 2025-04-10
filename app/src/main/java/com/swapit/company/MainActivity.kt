package com.swapit.company

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
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
            val chatViewModel: ChatViewModel =
                viewModel(
                    factory =
                        ChatViewModel.factory(
                            repository = ChatRepository.instance(),
                            loginRepository = LoginRepository.instance(this),
                        ),
                )
            val alertViewModel: AlertViewModel =
                viewModel(
                    factory =
                        AlertViewModel.factory(
                            application,
                            repository = AlertRepository.instance(),
                            LoginRepository.instance(this),
                        ),
                )
            // ✅ LifecycleObserver 추가 (앱이 종료될 때 WebSocket 해제)
            lifecycle.addObserver(
                LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_STOP) {
                        alertViewModel.fcmRestore(application = application)
                        alertViewModel.disconnect()
                        chatViewModel.disconnect()
                    }
                },
            )
            navigationModule.NavigationGraph(
                navController,
                loginViewModel,
                chatViewModel,
                alertViewModel,
            )
        }
    }
}
