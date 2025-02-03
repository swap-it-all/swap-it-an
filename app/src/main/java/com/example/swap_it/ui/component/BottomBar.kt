package com.example.swap_it.ui.component
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.swap_it.R
import com.example.swap_it.ui.add_item.AddItemScreen
import com.example.swap_it.ui.chat_list.ChatListScreen
import com.example.swap_it.ui.constant.ADD
import com.example.swap_it.ui.constant.CHAT
import com.example.swap_it.ui.constant.PRODUCT
import com.example.swap_it.ui.constant.USER
import com.example.swap_it.ui.constant.SHOPPING
import com.example.swap_it.ui.product_list.ProductListScreen
import com.example.swap_it.ui.shopping_list.ShoppingListScreen
import com.example.swap_it.ui.theme.Gray2
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.White
import com.example.swap_it.ui.user_info.UserInfoScreen

class BottomBar {
    private val items = listOf<BottomNavItem>(
        BottomNavItem.Product,
        BottomNavItem.Shopping,
        BottomNavItem.Add,
        BottomNavItem.Chat,
        BottomNavItem.User
    )

    sealed class BottomNavItem(
        val id: Int, val icon: Int, val screenRoute: String
    ) {
        data object Product : BottomNavItem(1, R.drawable.ic_house, PRODUCT)
        data object Shopping : BottomNavItem(2, R.drawable.ic_shopping_bag, SHOPPING)
        data object Add : BottomNavItem(3, R.drawable.ic_add_plus_circle, ADD)
        data object Chat : BottomNavItem(4, R.drawable.ic_chat, CHAT)
        data object User : BottomNavItem(5, R.drawable.ic_user, USER)
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        BottomNavigation(
            backgroundColor = White,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
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
                    selectedContentColor = Primary,
                    unselectedContentColor = Gray2,
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


    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = BottomNavItem.Product.screenRoute) {
            composable(BottomNavItem.Product.screenRoute) {
                ProductListScreen().ProductListScreen()
            }
            composable(BottomNavItem.Shopping.screenRoute) {
                ShoppingListScreen().ShoppingListScreen()
            }
            composable(BottomNavItem.Add.screenRoute) {
                AddItemScreen().AddItemScreen()
            }
            composable(BottomNavItem.Chat.screenRoute) {
                ChatListScreen().ChatListScreen()
            }
            composable(BottomNavItem.User.screenRoute) {
                UserInfoScreen().UserInfoScreen()
            }
        }
    }



}