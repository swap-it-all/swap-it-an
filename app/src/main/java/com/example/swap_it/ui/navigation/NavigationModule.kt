package com.example.swap_it.ui.navigation

import android.media.tv.TvContract.Programs.Genres
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.swap_it.R
import com.example.swap_it.ui.add_item.AddItemScreen
import com.example.swap_it.ui.chat.ChatListScreen
import com.example.swap_it.ui.shopping.AlertScreen
import com.example.swap_it.ui.shopping.ProductListScreen
import com.example.swap_it.ui.swap.ShoppingListScreen
import com.example.swap_it.ui.theme.Gray2
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.White
import com.example.swap_it.ui.user.UserInfoScreen


class NavigationModule {





    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Product.screenRoute
        ) {
            composable(BottomNavItem.Product.screenRoute) {
                ProductListScreen().ProductListScreen(Modifier, navController)
            }
            composable(BottomNavItem.Shopping.screenRoute) {
                ShoppingListScreen().ShoppingListScreen(navController)
            }
            composable(BottomNavItem.Add.screenRoute) {
                AddItemScreen().AddItemScreen(navController)
            }
            composable(BottomNavItem.Chat.screenRoute) {
                ChatListScreen().ChatListScreen(navController)
            }
            composable(BottomNavItem.User.screenRoute) {
                UserInfoScreen().UserInfoScreen(navController)
            }
            composable("Alert") {
                AlertScreen().AlertScreen(modifier = Modifier, navController)
            }
        }
    }
}
