package com.example.swap_it

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.rememberNavController

import com.example.swap_it.ui.navigation.NavigationModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val navigationModule = NavigationModule()
            navigationModule.NavigationGraph(navController)
        }
    }
}
