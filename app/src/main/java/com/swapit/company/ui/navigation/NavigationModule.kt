package com.swapit.company.ui.navigation

import ShoppingViewModel
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.swapit.company.domain.repository.AlertRepository
import com.swapit.company.domain.repository.ChatRepository
import com.swapit.company.domain.repository.LoginRepository
import com.swapit.company.domain.repository.ProductRepository
import com.swapit.company.domain.repository.ReportRepository
import com.swapit.company.domain.repository.SwapRepository
import com.swapit.company.domain.repository.UserRepository
import com.swapit.company.ui.alert.AlertScreen
import com.swapit.company.ui.alert.AlertViewModel
import com.swapit.company.ui.auth.LoginScreen
import com.swapit.company.ui.auth.LoginViewModel
import com.swapit.company.ui.chat.ChatListScreen
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.chat.room.ChatRoomScreen
import com.swapit.company.ui.post.PostProductScreen
import com.swapit.company.ui.post.PostProductViewModel
import com.swapit.company.ui.search.SearchScreen
import com.swapit.company.ui.shopping.ShoppingScreen
import com.swapit.company.ui.shopping.detail.ShoppingDetailScreen
import com.swapit.company.ui.shopping.detail.ShoppingDetailViewModel
import com.swapit.company.ui.shopping.detail.report.ReportScreen
import com.swapit.company.ui.shopping.detail.report.ReportViewModel
import com.swapit.company.ui.shopping.detail.select.MyProductSelectScreen
import com.swapit.company.ui.shopping.detail.select.MyProductSelectViewModel
import com.swapit.company.ui.splash.SplashScreen
import com.swapit.company.ui.swap.SwapScreen
import com.swapit.company.ui.swap.SwapViewModel
import com.swapit.company.ui.swap.received.ReceivedSwapScreen
import com.swapit.company.ui.swap.received.detail.ReceivedSwapDetailScreen
import com.swapit.company.ui.swap.sent.SentSwapScreen
import com.swapit.company.ui.user.UserInfoScreen
import com.swapit.company.ui.user.UserInfoViewModel
import com.swapit.company.ui.user.profile.ProfileEditScreen
import com.swapit.company.ui.user.setting.SettingScreen
import com.swapit.company.ui.user.setting.withdraw.WithdrawScreen

class NavigationModule {
    @Composable
    fun NavigationGraph(
        navController: NavHostController,
        loginViewModel: LoginViewModel,
        application: Application,
    ) {
        val userInfoViewModel = UserInfoViewModel(UserRepository.instance(LocalContext.current))
        val swapViewModel = SwapViewModel(SwapRepository.instance())
        val chatViewModel =
            ChatViewModel(ChatRepository.instance(), LoginRepository.instance(LocalContext.current))
        val alertViewModel =
            AlertViewModel(application = application, AlertRepository.instance(), LoginRepository.instance(LocalContext.current))
        NavHost(
            navController = navController,
            startDestination = NavItem.Splash.screenRoute,
        ) {
            composable(
                NavItem.Report.screenRoute + "/{goodsId}",
                arguments =
                    listOf(
                        navArgument("goodsId") {
                            type = NavType.StringType
                        },
                    ),
            ) {
                ReportScreen(
                    navController,
                    goodsId = it.arguments?.getString("goodsId") ?: "",
                    reportViewModel = viewModel(factory = ReportViewModel.factory(ReportRepository.instance())),
                )
            }
            composable(NavItem.Withdraw.screenRoute) {
                WithdrawScreen(navController, userInfoViewModel, loginViewModel)
            }
            composable(NavItem.Setting.screenRoute) {
                SettingScreen(navController, loginViewModel = loginViewModel, alertViewModel = alertViewModel)
            }

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
                    alertViewModel,
                    chatViewModel,
                    application,
                )
            }
            composable(NavItem.Swap.screenRoute) {
                SwapScreen(
                    navController,
                    swapViewModel,
                    alertViewModel,
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
                ChatListScreen(
                    navController,
                    viewModel = chatViewModel,
                    alertViewModel,
                )
            }
            composable(NavItem.User.screenRoute) {
                UserInfoScreen(
                    navController = navController,
                    viewModel =
                    userInfoViewModel,
                )
            }
            composable(NavItem.Alert.screenRoute) {
                AlertScreen(
                    navController,
                    viewModel = alertViewModel,
                )
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
                    userInfoViewModel = userInfoViewModel,
                    swapViewModel = swapViewModel,
                    chatViewModel = chatViewModel,
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
            composable(
                NavItem.ChatRoom.screenRoute + "/{chatroomId}",
                arguments =
                    listOf(
                        navArgument("chatroomId") {
                            type = NavType.StringType
                        },
                    ),
            ) { backStackEntry ->
                ChatRoomScreen(
                    navController,
                    backStackEntry.arguments?.getString("chatroomId") ?: "",
                    chatViewModel,
                    userInfoViewModel,
                )
            }
        }
    }
}
