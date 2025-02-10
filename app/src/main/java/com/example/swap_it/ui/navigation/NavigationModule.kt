package com.example.swap_it.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swap_it.ui.add_item.AddItemScreen
import com.example.swap_it.ui.alert.AlertScreen
import com.example.swap_it.ui.chat.ChatListScreen
import com.example.swap_it.ui.post.PostProductScreen
import com.example.swap_it.ui.post.PostProductViewModel
import com.example.swap_it.ui.search.SearchScreen
import com.example.swap_it.ui.shopping.ShoppingScreen
import com.example.swap_it.ui.swap.SwapScreen
import com.example.swap_it.ui.user.UserInfoScreen


class NavigationModule {





    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Shopping.screenRoute
        ) {
            composable(BottomNavItem.Shopping.screenRoute) {
                ShoppingScreen(Modifier, navController)
            }
            composable(BottomNavItem.Swap.screenRoute) {
                SwapScreen(Modifier,navController)
            }
            composable(BottomNavItem.Add.screenRoute) {
                PostProductScreen(viewModel = PostProductViewModel(),/* navController = navController*/)
            }
            composable(BottomNavItem.Chat.screenRoute) {
                ChatListScreen().ChatListScreen(navController)
            }
            composable(BottomNavItem.User.screenRoute) {
                UserInfoScreen().UserInfoScreen(navController)
            }
            composable("Alert") {
                AlertScreen(modifier = Modifier, navController)
            }
            composable("Search") {
                SearchScreen(Modifier, navController)
            }
        }
    }
}
