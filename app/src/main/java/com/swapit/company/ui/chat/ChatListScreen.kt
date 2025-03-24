package com.swapit.company.ui.chat

import androidx.compose.foundation.background
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
import com.swapit.company.ui.component.AppBar
import com.swapit.company.ui.component.BottomNavigationBar
import com.swapit.company.ui.theme.BackgroundColor

@Composable
fun ChatListScreen(
    navController: NavHostController,
    viewModel: ChatViewModel,
) {
    viewModel.fetchChatRoomList()
    Scaffold(
        topBar = {
            AppBar(navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { contentPadding ->

        LazyColumn(
            modifier =
                Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .background(BackgroundColor),
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                if (viewModel.chatRoomList.value.isEmpty()) {
                    NoChatIconSection()
                }
            }
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
