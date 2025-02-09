package com.example.swap_it.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swap_it.ui.add_item.AddItemScreen
import com.example.swap_it.ui.chat.ChatListScreen
import com.example.swap_it.ui.search.Search
import com.example.swap_it.ui.alert.Alert
import com.example.swap_it.ui.shopping.Shopping
import com.example.swap_it.ui.swap.ShoppingListScreen
import com.example.swap_it.ui.user.UserInfoScreen


class NavigationModule {





    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Shopping.screenRoute
        ) {
            composable(BottomNavItem.Shopping.screenRoute) {
                Shopping().ShoppingScreen(Modifier, navController)
            }
            composable(BottomNavItem.Swap.screenRoute) {
                ShoppingListScreen().ShoppingListScreen(navController)
            }
            composable(BottomNavItem.Post.screenRoute) {
                AddItemScreen().AddItemScreen(navController)
            }
            composable(BottomNavItem.Chat.screenRoute) {
                ChatListScreen().ChatListScreen(navController)
            }
            composable(BottomNavItem.User.screenRoute) {
                UserInfoScreen().UserInfoScreen(navController)
            }
            composable("Alert") {
                Alert().AlertScreen(modifier = Modifier, navController)
            }
            composable("Search") {
                Search().SearchScreen(Modifier, navController)
            }
        }
    }
}
