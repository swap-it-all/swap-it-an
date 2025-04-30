package com.swapit.oopswap.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.swapit.oopswap.ui.alert.AlertViewModel
import com.swapit.oopswap.ui.component.AppBar
import com.swapit.oopswap.ui.component.BottomNavigationBar
import com.swapit.oopswap.ui.theme.BackgroundColor

@Composable
fun ChatListScreen(
    navController: NavHostController,
    viewModel: ChatViewModel,
    alertViewModel: AlertViewModel,
) {
    viewModel.fetchChatRoomList()
    Scaffold(
        topBar = {
            AppBar(navController = navController, alertCount = alertViewModel.alertList.value.size)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
            if (viewModel.chatRoomList.isEmpty()) {
                NoChatIconSection()
            }
            LazyColumn {
                items(
                    viewModel.chatRoomList.size,
                    key = { index -> viewModel.chatRoomList[index].recentChatTime },
                ) { index ->
                    val chatCardData = viewModel.chatRoomList[index]
                    ChatCard(chatCardData = chatCardData, navController, viewModel)
                }
            }
        }
    }
}
