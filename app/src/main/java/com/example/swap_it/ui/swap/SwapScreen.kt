package com.example.swap_it.ui.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.swap_it.ui.component.AppBar
import com.example.swap_it.ui.component.BottomNavigationBar
import com.example.swap_it.ui.theme.BackgroundColor


@Composable
fun SwapScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppBar(navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor)
        ) {
            RequestedSwapSection(Modifier, navController)
            RequestSwapSection(Modifier, navController)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SwapScreenPreview() {
    SwapScreen(navController = NavHostController(context = LocalContext.current))
}
