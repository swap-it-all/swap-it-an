package com.example.swap_it.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swap_it.R
import com.example.swap_it.ui.theme.BackgroundColor

@Composable
fun AppBar(modifier: Modifier = Modifier, navController: NavHostController) {
    TopAppBar(
        elevation = 0.dp,
        title = {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "로고",
                modifier = modifier.size(32.dp),
            )
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate("Alert") {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_bell),
                    contentDescription = "알림"
                )
            }
        },
        backgroundColor = BackgroundColor,
    )
}