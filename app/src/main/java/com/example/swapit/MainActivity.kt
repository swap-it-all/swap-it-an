package com.example.swapit

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.swapit.domain.repository.LoginRepository
import com.example.swapit.ui.auth.LoginManager
import com.example.swapit.ui.auth.LoginViewModel
import com.example.swapit.ui.navigation.NavigationModule

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
            navigationModule.NavigationGraph(navController, loginViewModel)
        }
    }
}
