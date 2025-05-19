package com.swapit.oopswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.swapit.oopswap.data.datasource.remote.RetrofitModule
import com.swapit.oopswap.data.datasource.remote.ServiceModule
import com.swapit.oopswap.data.datasource.remote.StompModule
import com.swapit.oopswap.data.datasource.remote.createNotificationChannel
import com.swapit.oopswap.domain.repository.AlertRepository
import com.swapit.oopswap.domain.repository.ChatRepository
import com.swapit.oopswap.domain.repository.LoginRepository
import com.swapit.oopswap.ui.alert.AlertViewModel
import com.swapit.oopswap.ui.auth.LoginManager
import com.swapit.oopswap.ui.auth.LoginViewModel
import com.swapit.oopswap.ui.chat.ChatViewModel
import com.swapit.oopswap.ui.navigation.AppNavigation
import com.swapit.oopswap.ui.navigation.NavItem
import com.swapit.oopswap.ui.navigation.NavigationModule
import kotlinx.coroutines.flow.filter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            createNotificationChannel(this)
            val loginViewModel: LoginViewModel =
                viewModel(
                    factory =
                        LoginViewModel.factory(
                            application,
                            LoginRepository.instance(this),
                            LoginManager(this),
                        ),
                )

            val stompModule = StompModule(
                LoginRepository.instance(this),
                application,
                onLogout = { loginViewModel.logout() })

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

            //❶ NavController 를 여기서 remember 해서 호이스팅
            val navController = rememberNavController()

            //❷ Activity(Compose) 레벨에서 한 번만 수집
            LaunchedEffect(loginViewModel.isLoggedIn) {
                loginViewModel.isLoggedIn
                    .filter { loggedIn -> !loggedIn }
                    .collect {
                        navController.popBackStack(
                            navController.graph.startDestinationId,
                            inclusive = true
                        )
                        navController.navigate(NavItem.Login.screenRoute) {
                            launchSingleTop = true
                        }
                    }
            }

            //❸ AppNavigation 에 navController 만 넘겨 줌
            AppNavigation(
                navController = navController,
                loginViewModel = loginViewModel,
                chatViewModel = chatViewModel,
                alertViewModel = alertViewModel,
                stompModule = stompModule,
                application = application
            )

// ✅ LifecycleObserver 추가 (앱이 종료될 때 WebSocket 해제)
            lifecycle.addObserver(
                LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_STOP) {
                        stompModule.disconnect()
                    }
                },
            )
        }
    }
}
/*AppNavigation(
    loginViewModel,
    chatViewModel,
    alertViewModel,
    stompModule,
    application,
)
/*navigationModule.NavigationGraph(
   navController,
   loginViewModel,
   chatViewdel,
   alertViewModel,
   stompModule,
   application,
)*/