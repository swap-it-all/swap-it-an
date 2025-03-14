package com.example.swapit.ui.navigation

import ShoppingViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.swapit.domain.repository.ProductRepository
import com.example.swapit.domain.repository.SwapRepository
import com.example.swapit.domain.repository.UserRepository
import com.example.swapit.ui.alert.AlertScreen
import com.example.swapit.ui.auth.LoginScreen
import com.example.swapit.ui.auth.LoginViewModel
import com.example.swapit.ui.chat.ChatScreen
import com.example.swapit.ui.chat.room.ChatRoomScreen
import com.example.swapit.ui.post.PostProductScreen
import com.example.swapit.ui.post.PostProductViewModel
import com.example.swapit.ui.search.SearchScreen
import com.example.swapit.ui.shopping.ShoppingScreen
import com.example.swapit.ui.shopping.detail.ShoppingDetailScreen
import com.example.swapit.ui.shopping.detail.ShoppingDetailViewModel
import com.example.swapit.ui.shopping.detail.select.MyProductSelectScreen
import com.example.swapit.ui.shopping.detail.select.MyProductSelectViewModel
import com.example.swapit.ui.splash.SplashScreen
import com.example.swapit.ui.swap.SwapScreen
import com.example.swapit.ui.swap.SwapViewModel
import com.example.swapit.ui.swap.received.ReceivedSwapScreen
import com.example.swapit.ui.swap.received.detail.ReceivedSwapDetailScreen
import com.example.swapit.ui.swap.sent.SentSwapScreen
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
        val swapViewModel = SwapViewModel(SwapRepository.instance())
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
                    viewModel(
                        factory =
                            ShoppingViewModel.factory(
                                ProductRepository.instance(context = LocalContext.current),
                            ),
                    ),
                )
            }
            composable(NavItem.Swap.screenRoute) {
                SwapScreen(
                    navController,
                    swapViewModel,
                )
            }
            composable(
                route = NavItem.ReceivedDetailSwap.screenRoute + "/{goodsId}",
                arguments =
                    listOf(
                        navArgument("goodsId") {
                            type = NavType.StringType
                        },
                    ),
            ) { backStackEntry ->
                swapViewModel.fetchReceivedSwapProductsResult(
                    backStackEntry.arguments?.getString("goodsId")?.toLong() ?: 0,
                )
                ReceivedSwapDetailScreen(
                    navController,
                    myProductName = swapViewModel.myGoodsTitle.value,
                    receivedSwapProducts = swapViewModel.receivedSwapProductsResult.value,
                )
            }
            composable(NavItem.ReceivedSwap.screenRoute) {
                ReceivedSwapScreen(
                    navController = navController,
                    receivedSwaps = swapViewModel.receivedSwap.value,
                )
            }
            composable(NavItem.SentSwap.screenRoute) {
                SentSwapScreen(
                    navController = navController,
                    sentSwaps = swapViewModel.sentSwap.value,
                )
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
                    userInfoViewModel,
                )
            }
            composable(NavItem.Alert.screenRoute) {
                AlertScreen(navController)
            }
            composable(NavItem.Search.screenRoute) {
                SearchScreen(
                    navController,
                    viewModel =
                        viewModel(
                            factory =
                                ShoppingViewModel.factory(
                                    ProductRepository.instance(context = LocalContext.current),
                                ),
                        ),
                )
            }
            composable(
                route = NavItem.ShoppingDetail.screenRoute + "/{goodsId}",
                arguments =
                    listOf(
                        navArgument("goodsId") {
                            type = NavType.StringType
                        },
                    ),
            ) { backStackEntry ->
                ShoppingDetailScreen(
                    Modifier,
                    navController,
                    shoppingDetailViewModel =
                        viewModel(
                            factory =
                                ShoppingDetailViewModel.factory(
                                    ProductRepository.instance(context = LocalContext.current),
                                    backStackEntry.arguments?.getString("goodsId") ?: "",
                                ),
                        ),
                    myProductSelectViewModel =
                        viewModel(
                            factory =
                                MyProductSelectViewModel.factory(
                                    ProductRepository.instance(LocalContext.current),
                                ),
                        ),
                    swapViewModel = swapViewModel,
                )
            }
            composable(
                route = NavItem.MyProductSelection.screenRoute + "/{targetProductId}",
                arguments =
                    listOf(
                        navArgument("targetProductId") {
                            type = NavType.StringType
                        },
                    ),
            ) { backStackEntry ->
                MyProductSelectScreen(
                    navController,
                    viewModel =
                        viewModel(
                            factory =
                                MyProductSelectViewModel.factory(
                                    ProductRepository.instance(LocalContext.current),
                                ),
                        ),
                    targetProductId =
                        backStackEntry.arguments?.getString("targetProductId")
                            ?.toLong() ?: 0,
                )
            }
            composable(NavItem.ProfileEdit.screenRoute) {
                ProfileEditScreen(
                    navController,
                    viewModel = userInfoViewModel,
                )
            }
            composable(NavItem.ChatRoom.screenRoute) {
                ChatRoomScreen(navController)
            }
        }
    }
}
