package com.example.swap_it

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.ui.component.BottomBar

import com.example.swap_it.ui.theme.SwapitTheme

class RootScreen {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NotConstructor")
    @Composable
    fun MenuScreen(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        val bottomBar = BottomBar()
        Scaffold(
            topBar = {
                TopAppBar()
            },
            bottomBar = {
                bottomBar.BottomNavigationBar(navController)
            }
        ) {
            Box(Modifier.padding(it)){
                bottomBar.NavigationGraph(navController = navController)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(modifier: Modifier = Modifier) {
        TopAppBar(
            title = {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = "로고",
                    modifier = modifier.size(32.dp),
                )
            },
            actions = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_bell),
                        contentDescription = "알림"
                    )
                }
            }
        )
    }



    @Preview(showBackground = true)
    @Composable
    fun ProductScreenPreview() {
        SwapitTheme {
            MenuScreen()
        }
    }
}