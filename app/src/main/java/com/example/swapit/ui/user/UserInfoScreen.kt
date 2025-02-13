package com.example.swapit.ui.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.swapit.ui.component.AppBar
import com.example.swapit.ui.component.BottomNavigationBar
import com.example.swapit.ui.navigation.NavigationModule

@SuppressLint("NotConstructor")
class UserInfoScreen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun UserInfoScreen(navController: NavHostController) {
        val navigationModule = NavigationModule()
        Scaffold(
            topBar = {
                AppBar(navController = navController)
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Blue),
            ) {
                Text(
                    text = "유저 정보",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}
