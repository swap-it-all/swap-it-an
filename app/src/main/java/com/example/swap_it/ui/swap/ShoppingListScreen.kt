package com.example.swap_it.ui.swap

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.swap_it.ui.component.AppBar
import com.example.swap_it.ui.component.BottomNavigationBar


import com.example.swap_it.ui.navigation.NavigationModule

class ShoppingListScreen {
    @SuppressLint("NotConstructor", "UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ShoppingListScreen(navController: NavHostController) {
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(Gray)
            ) {
                Text(
                    text = "거래",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }

}