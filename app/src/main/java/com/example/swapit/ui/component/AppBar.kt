package com.example.swapit.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swapit.R
import com.example.swapit.ui.navigation.NavItem
import com.example.swapit.ui.theme.BackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
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
                onClick = {
                    navController.navigate(NavItem.Alert.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_bell),
                    contentDescription = "알림",
                )
            }
        },
        colors =
            TopAppBarColors(
                containerColor = BackgroundColor,
                navigationIconContentColor = BackgroundColor,
                actionIconContentColor = BackgroundColor,
                scrolledContainerColor = BackgroundColor,
                titleContentColor = BackgroundColor,
            ),
    )
}
