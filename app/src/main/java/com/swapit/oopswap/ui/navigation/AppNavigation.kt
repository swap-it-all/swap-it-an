package com.swapit.oopswap.ui.navigation

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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.swapit.oopswap.data.datasource.remote.StompModule
import com.swapit.oopswap.domain.repository.*
import com.swapit.oopswap.ui.alert.AlertScreen
import com.swapit.oopswap.ui.alert.AlertViewModel
import com.swapit.oopswap.ui.auth.LoginScreen
import com.swapit.oopswap.ui.auth.LoginViewModel
import com.swapit.oopswap.ui.chat.ChatListScreen
import com.swapit.oopswap.ui.chat.ChatViewModel
import com.swapit.oopswap.ui.chat.room.ChatRoomScreen
import com.swapit.oopswap.ui.post.PostProductScreen
import com.swapit.oopswap.ui.post.PostProductViewModel
import com.swapit.oopswap.ui.search.SearchScreen
import com.swapit.oopswap.ui.shopping.ShoppingScreen
import com.swapit.oopswap.ui.shopping.detail.ShoppingDetailScreen
import com.swapit.oopswap.ui.shopping.detail.ShoppingDetailViewModel
import com.swapit.oopswap.ui.shopping.detail.report.ReportScreen
import com.swapit.oopswap.ui.shopping.detail.report.ReportViewModel
import com.swapit.oopswap.ui.shopping.detail.select.MyProductSelectScreen
import com.swapit.oopswap.ui.shopping.detail.select.MyProductSelectViewModel
import com.swapit.oopswap.ui.splash.SplashScreen
import com.swapit.oopswap.ui.swap.SwapScreen
import com.swapit.oopswap.ui.swap.SwapViewModel
import com.swapit.oopswap.ui.swap.received.ReceivedSwapScreen
import com.swapit.oopswap.ui.swap.received.detail.ReceivedSwapDetailScreen
import com.swapit.oopswap.ui.swap.sent.SentSwapScreen
import com.swapit.oopswap.ui.user.UserInfoScreen
import com.swapit.oopswap.ui.user.UserInfoViewModel
import com.swapit.oopswap.ui.user.profile.ProfileEditScreen
import com.swapit.oopswap.ui.user.setting.SettingScreen
import com.swapit.oopswap.ui.user.setting.withdraw.WithdrawScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    chatViewModel: ChatViewModel,
    alertViewModel: AlertViewModel,
    stompModule: StompModule,
    application: Application
) {
    // 한 번만 NavHost 생성
    NavHost(navController, startDestination = NavItem.Splash.screenRoute) {

        // --- Splash & Login ---
        composable(NavItem.Splash.screenRoute) {
            SplashScreen(navController, loginViewModel)
        }
        composable(NavItem.Login.screenRoute) {
            LoginScreen(navController, loginViewModel)
        }

        // --- Shopping ---
        composable(NavItem.Shopping.screenRoute) {
            val shoppingVm: ShoppingViewModel = viewModel(
                modelClass = ShoppingViewModel::class.java,
                factory    = ShoppingViewModel.factory(ProductRepository.instance(LocalContext.current))
            )
            ShoppingScreen(navController, shoppingVm, alertViewModel, application)
        }

        // --- Swap ---
        composable(NavItem.Swap.screenRoute) {
            val swapVm = SwapViewModel(SwapRepository.instance())
            SwapScreen(navController, swapVm, alertViewModel)
        }

        // --- Received Swap Detail ---
        composable(
            route     = NavItem.ReceivedDetailSwap.screenRoute + "/{goodsId}",
            arguments = listOf(navArgument("goodsId") { type = NavType.StringType })
        ) { back ->
            val id = back.arguments!!.getString("goodsId")!!.toLong()
            val swapVm = SwapViewModel(SwapRepository.instance())
            swapVm.fetchReceivedSwapProductsResult(id)
            ReceivedSwapDetailScreen(
                navController,
                myProductName         = swapVm.myGoodsTitle.value,
                receivedSwapProducts = swapVm.receivedSwapProductsResult.value
            )
        }

        // --- Received Swap List ---
        composable(NavItem.ReceivedSwap.screenRoute) {
            val swapVm = SwapViewModel(SwapRepository.instance())
            ReceivedSwapScreen(navController, swapVm.receivedSwap.value)
        }

        // --- Sent Swap List ---
        composable(NavItem.SentSwap.screenRoute) {
            val swapVm = SwapViewModel(SwapRepository.instance())
            SentSwapScreen(navController, swapVm.sentSwap.value)
        }

        // --- Post Product ---
        composable(NavItem.Add.screenRoute) {
            val postVm: PostProductViewModel = viewModel(
                modelClass = PostProductViewModel::class.java,
                factory    = PostProductViewModel.factory(ProductRepository.instance(LocalContext.current))
            )
            PostProductScreen(navController, postVm)
        }

        // --- Chat List ---
        composable(NavItem.Chat.screenRoute) {
            ChatListScreen(navController, chatViewModel, alertViewModel)
        }

        // --- Chat Room ---
        composable(
            NavItem.ChatRoom.screenRoute + "/{chatroomId}",
            arguments = listOf(navArgument("chatroomId") { type = NavType.StringType })
        ) { back ->
            val roomId = back.arguments!!.getString("chatroomId")!!
            ChatRoomScreen(
                navController,
                roomId,
                chatViewModel,
                viewModel(
                    modelClass = UserInfoViewModel::class.java,
                    factory    = UserInfoViewModel.factory(UserRepository.instance(LocalContext.current))
                ),
                stompModule
            )
        }

        // --- Search ---
        composable(NavItem.Search.screenRoute) {
            val searchVm: ShoppingViewModel = viewModel(
                modelClass = ShoppingViewModel::class.java,
                factory    = ShoppingViewModel.factory(ProductRepository.instance(LocalContext.current))
            )
            SearchScreen(navController, searchVm)
        }

        // --- Shopping Detail ---
        composable(
            NavItem.ShoppingDetail.screenRoute + "/{goodsId}",
            arguments = listOf(navArgument("goodsId") { type = NavType.StringType })
        ) { back ->
            val id = back.arguments!!.getString("goodsId")!!
            val detailVm: ShoppingDetailViewModel = viewModel(
                modelClass = ShoppingDetailViewModel::class.java,
                factory    = ShoppingDetailViewModel.factory(ProductRepository.instance(LocalContext.current), id)
            )
            ShoppingDetailScreen(
                Modifier,
                navController,
                shoppingDetailViewModel = detailVm,
                userInfoViewModel      = viewModel(
                    modelClass = UserInfoViewModel::class.java,
                    factory    = UserInfoViewModel.factory(UserRepository.instance(LocalContext.current))
                ),
                swapViewModel          = SwapViewModel(SwapRepository.instance()),
                chatViewModel          = chatViewModel
            )
        }

        // --- My Product Selection ---
        composable(
            NavItem.MyProductSelection.screenRoute + "/{targetProductId}",
            arguments = listOf(navArgument("targetProductId") { type = NavType.StringType })
        ) { back ->
            val targetId = back.arguments!!.getString("targetProductId")!!.toLong()
            val selectVm: MyProductSelectViewModel = viewModel(
                modelClass = MyProductSelectViewModel::class.java,
                factory    = MyProductSelectViewModel.factory(ProductRepository.instance(LocalContext.current))
            )
            MyProductSelectScreen(navController, selectVm, targetProductId = targetId)
        }

        // --- Report ---
        composable(
            NavItem.Report.screenRoute + "/{goodsId}",
            arguments = listOf(navArgument("goodsId") { type = NavType.StringType })
        ) { back ->
            val goodsId = back.arguments!!.getString("goodsId")!!
            val reportVm: ReportViewModel = viewModel(
                modelClass = ReportViewModel::class.java,
                factory    = ReportViewModel.factory(ReportRepository.instance())
            )
            ReportScreen(navController, goodsId, reportVm)
        }

        // --- Alert & Settings & Profile ---
        composable(NavItem.Alert.screenRoute) {
            AlertScreen(navController, alertViewModel)
        }
        composable(NavItem.Setting.screenRoute) {
            SettingScreen(navController, alertViewModel, loginViewModel)
        }
        composable(NavItem.Withdraw.screenRoute) {
            WithdrawScreen(
                navController,
                viewModel(
                    modelClass = UserInfoViewModel::class.java,
                    factory    = UserInfoViewModel.factory(UserRepository.instance(LocalContext.current))
                ),
                loginViewModel
            )
        }
        composable(NavItem.User.screenRoute) {
            UserInfoScreen(
                navController,
                viewModel(
                    modelClass = UserInfoViewModel::class.java,
                    factory    = UserInfoViewModel.factory(UserRepository.instance(LocalContext.current))
                )
            )
        }
        composable(NavItem.ProfileEdit.screenRoute) {
            ProfileEditScreen(
                navController,
                viewModel(
                    modelClass = UserInfoViewModel::class.java,
                    factory    = UserInfoViewModel.factory(UserRepository.instance(LocalContext.current))
                )
            )
        }
    }
}
