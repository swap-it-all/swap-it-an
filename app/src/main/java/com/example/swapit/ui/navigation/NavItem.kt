package com.example.swapit.ui.navigation

import com.example.swapit.R

sealed class NavItem(
    val name: String,
    val icon: Int,
    val screenRoute: String,
) {
    data object Splash : NavItem("splash", R.drawable.ic_logo, SPLASH)

    data object Login : NavItem("로그인", R.drawable.ic_user, LOGIN)

    data object Shopping : NavItem("쇼핑", R.drawable.ic_house, SHOPPING)

    data object Swap : NavItem("스왑", R.drawable.ic_shopping_bag, SWAP)

    data object Add : NavItem("게시", R.drawable.ic_add_plus_circle, POST)

    data object Chat : NavItem("채팅", R.drawable.ic_chat, CHAT)

    data object User : NavItem("사용자", R.drawable.ic_user, USER)

    data object Alert : NavItem("알림", R.drawable.ic_bell, ALERT)

    data object Search : NavItem("검색", R.drawable.ic_search_magnifying, SEARCH)

    data object ShoppingDetail : NavItem("쇼핑 상세 화면", R.drawable.ic_shopping_bag, SHOPPING_DETAIL)

    data object MyProductSelection : NavItem("바꾸려는 물건 내 물건 선택", R.drawable.ic_shopping_bag, MY_PRODUCT_SELECT)

    data object ChatRoom : NavItem("채팅방", R.drawable.ic_chat, CHAT_ROOM)

    data object ProfileEdit : NavItem("프로필 수정", R.drawable.ic_user, PROFILE_EDIT)

    companion object {
        const val SPLASH = "SPLASH"
        const val CHAT_ROOM = "CHAT_ROOM"
        const val SEARCH = "SEARCH"
        const val ALERT = "ALERT"
        const val SHOPPING_DETAIL = "SHOPPING_DETAIL"
        const val MY_PRODUCT_SELECT = "MY_PRODUCT_SELECT"
        const val LOGIN = "LOGIN"
        const val SHOPPING = "SHOPPING"
        const val SWAP = "SWAP"
        const val POST = "POST"
        const val CHAT = "CHAT"
        const val USER = "USER"
        const val PROFILE_EDIT = "PROFILE_EDIT"
    }
}
