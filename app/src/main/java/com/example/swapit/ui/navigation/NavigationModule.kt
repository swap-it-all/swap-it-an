package com.example.swapit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swapit.domain.repository.ProductRepository
import com.example.swapit.ui.alert.AlertScreen
import com.example.swapit.ui.auth.LoginScreen
import com.example.swapit.ui.auth.LoginViewModel
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
    fun NavigationGraph(
        navController: NavHostController,
        loginViewModel: LoginViewModel,
    ) {
        val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
        val startDestination =
            if (isLoggedIn) BottomNavItem.Shopping.screenRoute else BottomNavItem.Login.screenRoute

        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(BottomNavItem.Login.screenRoute) {
                LoginScreen(
                    navController = navController,
                    viewModel = loginViewModel,
                )
            }

            composable(BottomNavItem.Shopping.screenRoute) {
                ShoppingScreen(navController)
            }
            composable(BottomNavItem.Swap.screenRoute) {
                SwapScreen(navController)
            }
            composable(BottomNavItem.Add.screenRoute) {
                PostProductScreen(
                    navController = navController,
                    viewModel =
                        viewModel(
                            factory =
                                PostProductViewModel.factory(
                                    ProductRepository.instance(
                                        LocalContext.current,
                                    ),
                                ),
                        ),
                )
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
