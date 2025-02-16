package com.example.swapit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swapit.ui.alert.AlertScreen
import com.example.swapit.ui.chat.ChatListScreen
import com.example.swapit.ui.post.PostProductScreen
import com.example.swapit.ui.post.PostProductViewModel
import com.example.swapit.ui.search.SearchScreen
import com.example.swapit.ui.search.SearchViewModel
import com.example.swapit.ui.shopping.ShoppingScreen
import com.example.swapit.ui.shopping.detail.ShoppingDetailScreen
import com.example.swapit.ui.shopping.detail.defaultShoppingDetailData
import com.example.swapit.ui.shopping.detail.select.MyProductSelectionScreen
import com.example.swapit.ui.swap.SwapScreen
import com.example.swapit.ui.user.UserInfoScreen

class NavigationModule {
    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Shopping.screenRoute,
        ) {
            composable(BottomNavItem.Shopping.screenRoute) {
                ShoppingScreen(navController)
            }
            composable(BottomNavItem.Swap.screenRoute) {
                SwapScreen(navController)
            }
            composable(BottomNavItem.Add.screenRoute) {
                PostProductScreen(viewModel = PostProductViewModel())
            }
            composable(BottomNavItem.Chat.screenRoute) {
                ChatListScreen().ChatListScreen(navController)
            }
            composable(BottomNavItem.User.screenRoute) {
                UserInfoScreen().UserInfoScreen(navController)
            }
            composable("Alert") {
                AlertScreen(navController)
            }
            composable("Search") {
                SearchScreen(navController, viewModel = SearchViewModel())
            }
            composable("ShoppingDetail") {
                ShoppingDetailScreen(Modifier, navController, defaultShoppingDetailData)
            }
            composable("MyProductSelection") {
                MyProductSelectionScreen(navController)
            }
        }
    }
}
