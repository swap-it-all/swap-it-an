package com.example.swapit.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swapit.ui.component.AppBar
import com.example.swapit.ui.component.BottomNavigationBar
import com.example.swapit.ui.theme.BackgroundColor

@Composable
fun ChatScreen(navController: NavHostController) {
    val chatList = 1
    Scaffold(
        topBar = {
            AppBar(navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { contentPadding ->
        if (chatList == 0)
            {
                NoChatIconSection(contentPadding)
            }
        LazyColumn(
            modifier =
                Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .background(BackgroundColor),
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(6) {
                ChatCard(chatCardData = _chatCardData)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ChatListScreenPreview() {
    val navController = NavHostController(LocalContext.current)
    ChatScreen(navController)
}
