package com.example.swapit.ui.navigation

import ShoppingViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swapit.domain.repository.ProductRepository
import com.example.swapit.domain.repository.ShoppingRepository
import com.example.swapit.domain.repository.UserRepository
import com.example.swapit.ui.alert.AlertScreen
import com.example.swapit.ui.auth.LoginScreen
import com.example.swapit.ui.auth.LoginViewModel
import com.example.swapit.ui.chat.ChatScreen
import com.example.swapit.ui.chat.room.ChatRoomScreen
import com.example.swapit.ui.post.PostProductScreen
import com.example.swapit.ui.post.PostProductViewModel
import com.example.swapit.ui.search.SearchScreen
import com.example.swapit.ui.search.SearchViewModel
import com.example.swapit.ui.shopping.ShoppingScreen
import com.example.swapit.ui.shopping.detail.ShoppingDetailScreen
import com.example.swapit.ui.shopping.detail.defaultShoppingDetailData
import com.example.swapit.ui.shopping.detail.select.MyProductSelectionScreen
import com.example.swapit.ui.splash.SplashScreen
import com.example.swapit.ui.swap.SwapScreen
import com.example.swapit.ui.user.UserInfoScreen
import com.example.swapit.ui.user.UserInfoViewModel
import com.example.swapit.ui.user.profile.ProfileEditScreen

class NavigationModule {
    @Composable
    fun NavigationGraph(
        navController: NavHostController,
        loginViewModel: LoginViewModel,
    ) {
        val userInfoViewModel = UserInfoViewModel(UserRepository.instance(LocalContext.current))
        NavHost(
            navController = navController,
            startDestination = NavItem.Splash.screenRoute,
        ) {
            composable(NavItem.Splash.screenRoute) {
                SplashScreen(navController, loginViewModel)
            }

            composable(NavItem.Login.screenRoute) {
                LoginScreen(
                    navController = navController,
                    viewModel = loginViewModel,
                )
            }

            composable(NavItem.Shopping.screenRoute) {
                ShoppingScreen(
                    navController,
                    viewModel =
                    viewModel(
                        factory =
                        ShoppingViewModel.factory(
                            ShoppingRepository.instance(),
                        ),
                    ),
                )
            }
            composable(NavItem.Swap.screenRoute) {
                SwapScreen(navController)
            }
            composable(NavItem.Add.screenRoute) {
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
            composable(NavItem.Chat.screenRoute) {
                ChatScreen(navController)
            }
            composable(NavItem.User.screenRoute) {
                UserInfoScreen(
                    navController = navController,
                    viewModel =
                    userInfoViewModel
                )
            }
            composable(NavItem.Alert.screenRoute) {
                AlertScreen(navController)
            }
            composable(NavItem.Search.screenRoute) {
                SearchScreen(navController, viewModel = SearchViewModel())
            }
            composable(NavItem.ShoppingDetail.screenRoute) {
                ShoppingDetailScreen(Modifier, navController, defaultShoppingDetailData)
            }
            composable(NavItem.MyProductSelection.screenRoute) {
                MyProductSelectionScreen(
                    navController,
                    viewModel =
                    viewModel(),
                )
            }
            composable(NavItem.ProfileEdit.screenRoute) {
                ProfileEditScreen(
                    navController,
                    viewModel = userInfoViewModel
                )
            }
            composable(NavItem.ChatRoom.screenRoute) {
                ChatRoomScreen(navController)
            }
        }
    }
}
