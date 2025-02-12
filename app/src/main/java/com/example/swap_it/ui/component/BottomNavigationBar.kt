package com.example.swap_it.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.swap_it.ui.navigation.BottomNavItem
import com.example.swap_it.ui.theme.Gray2
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.Typography
import com.example.swap_it.ui.theme.White
private val items = listOf<BottomNavItem>(
    BottomNavItem.Shopping,
    BottomNavItem.Swap,
    BottomNavItem.Add,
    BottomNavItem.Chat,
    BottomNavItem.User
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar (
        containerColor = Gray6,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.screenRoute,
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp),
                        tint = if (currentRoute == item.screenRoute) Primary else Gray4
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    unselectedIconColor = Gray4,
                    indicatorColor = Gray6,
                ),
                label = { Text(item.name, style = Typography.labelSmall) },
                alwaysShowLabel = false,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}