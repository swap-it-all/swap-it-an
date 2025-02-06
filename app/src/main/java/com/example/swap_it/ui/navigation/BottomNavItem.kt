package com.example.swap_it.ui.navigation

import com.example.swap_it.R


sealed class BottomNavItem(
    val icon: Int,
    val screenRoute: String
) {
    data object Product : BottomNavItem(R.drawable.ic_house, PRODUCT)
    data object Shopping : BottomNavItem(R.drawable.ic_shopping_bag, SHOPPING)
    data object Add : BottomNavItem(R.drawable.ic_add_plus_circle, ADD)
    data object Chat : BottomNavItem(R.drawable.ic_chat, CHAT)
    data object User : BottomNavItem(R.drawable.ic_user, USER)

    companion object {
        const val PRODUCT = "PRODUCT"
        const val SHOPPING = "SHOPPING"
        const val ADD = "ADD"
        const val CHAT = "CHAT"
        const val USER = "USER"
    }
}
