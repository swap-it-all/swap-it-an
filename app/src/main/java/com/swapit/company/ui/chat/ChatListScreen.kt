package com.swapit.company.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.ui.alert.AlertViewModel
import com.swapit.company.ui.component.AppBar
import com.swapit.company.ui.component.BottomNavigationBar
import com.swapit.company.ui.theme.BackgroundColor

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
            if (viewModel.chatRoomList.value.isEmpty()) {
                NoChatIconSection()
            }
            LazyColumn {
                items(
                    viewModel.chatRoomList.value.size,
                    key = { index -> viewModel.chatRoomList.value[index].recentChatTime },
                ) { index ->
                    val chatCardData = viewModel.chatRoomList.value[index]
                    ChatCard(chatCardData = chatCardData, navController, viewModel)
                }
            }
        }
    }
}
