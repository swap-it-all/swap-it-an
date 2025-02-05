package com.example.swap_it.ui.menu

import android.annotation.SuppressLint
import android.media.tv.TvContract.Programs.Genres.SHOPPING
import android.os.Build.PRODUCT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.ui.add_item.AddItemScreen
import com.example.swap_it.ui.chat_list.ChatListScreen
import com.example.swap_it.ui.product_list.AlertScreen
import com.example.swap_it.ui.product_list.ProductListScreen
import com.example.swap_it.ui.shopping_list.ShoppingListScreen
import com.example.swap_it.ui.theme.Gray2
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Primary

import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.White
import com.example.swap_it.ui.user_info.UserInfoScreen

const val PRODUCT = "PRODUCT"
const val SHOPPING = "SHOPPING"
const val ADD = "ADD"
const val CHAT = "CHAT"
const val USER = "USER"
class MenuScreen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NotConstructor")
    @Composable
    fun MenuScreen(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        val navigationModule = NavigationModule()
        navigationModule.NavigationGraph(navController = navController)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBar(modifier: Modifier = Modifier, navController: NavHostController) {
        TopAppBar(
            title = {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = "로고",
                    modifier = modifier.size(32.dp),
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        navController.navigate("Alert"){
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_bell),
                        contentDescription = "알림"
                    )
                }
            }
        )
    }


    class NavigationModule {
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
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Product.screenRoute
            ) {
                composable(BottomNavItem.Product.screenRoute) {
                    ProductListScreen().ProductListScreen(Modifier,navController)
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
                    AlertScreen().AlertScreen(modifier = Modifier,navController)
                }
            }
        }


    }


    @Preview(showBackground = true)
    @Composable
    fun ProductScreenPreview() {
        SwapitTheme {
            MenuScreen()
        }
    }
}