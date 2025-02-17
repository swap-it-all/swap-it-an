package com.example.swapit.ui.navigation

import com.example.swapit.R

sealed class BottomNavItem(
    val name: String,
    val icon: Int,
    val screenRoute: String,
) {
    data object Login : BottomNavItem("로그인", R.drawable.ic_user, LOGIN)

    data object Shopping : BottomNavItem("쇼핑", R.drawable.ic_house, SHOPPING)

    data object Swap : BottomNavItem("스왑", R.drawable.ic_shopping_bag, SWAP)

    data object Add : BottomNavItem("게시", R.drawable.ic_add_plus_circle, POST)

    data object Chat : BottomNavItem("채팅", R.drawable.ic_chat, CHAT)

    data object User : BottomNavItem("사용자", R.drawable.ic_user, USER)

    companion object {
        const val LOGIN = "LOGIN"
        const val SHOPPING = "SHOPPING"
        const val SWAP = "SWAP"
        const val POST = "POST"
        const val CHAT = "CHAT"
        const val USER = "USER"
    }
}
