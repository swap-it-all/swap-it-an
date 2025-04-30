package com.swapit.oopswap.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.oopswap.R
import com.swapit.oopswap.ui.navigation.NavItem
import com.swapit.oopswap.ui.theme.BackgroundColor
import com.swapit.oopswap.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    alertCount: Int,
) {
    TopAppBar(
        title = {
            IconButton(
                onClick = {
                    navController.navigate(NavItem.Shopping.screenRoute)
                },
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = "로고",
                    modifier = modifier.size(32.dp),
                )
            }
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
                Box {
                    Image(
                        painter = painterResource(R.drawable.ic_bell),
                        contentDescription = "알림",
                    )
                    if (alertCount != 0) {
                        Box(
                            modifier =
                                Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .align(androidx.compose.ui.Alignment.TopEnd)
                                    .background(
                                        Red,
                                    ),
                        ) {
                        }
                    }
                }
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

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppBar(Modifier, NavHostController(LocalContext.current), 0)
}
