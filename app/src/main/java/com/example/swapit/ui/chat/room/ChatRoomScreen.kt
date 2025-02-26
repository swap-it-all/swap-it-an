package com.example.swapit.ui.chat.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.swapit.data.model.Chat
import com.example.swapit.ui.theme.BackgroundColor
import java.time.LocalDateTime

@Composable
fun ChatRoomScreen(navController: NavHostController) {
    Scaffold(
        modifier =
            Modifier
                .background(BackgroundColor)
                .imePadding(),
        topBar = {
            ChatRoomAppBar(navController = navController)
        },
    ) { contentPadding ->
        Column(
            modifier =
                Modifier
                    .padding(contentPadding)
                    .background(BackgroundColor),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            ChatRoomProduct()
            ChatRoomContent(modifier = Modifier.weight(1f))
            BottomChatBar()
        }
    }
}

val chats: List<Chat> =
    listOf(
        Chat(1, "text", "안녕하세요", 1, LocalDateTime.now()),
        Chat(2, "text", "안녕하세요", 2, LocalDateTime.now()),
        Chat(3, "text", "안녕하세요", 1, LocalDateTime.now()),
        Chat(4, "text", "안녕하세요", 2, LocalDateTime.now()),
    ) // todo: 채팅 데이터 만들기

@Composable
@Preview(showBackground = true)
fun ChatRoomScreenPreview() {
    ChatRoomScreen(navController = NavHostController(LocalContext.current))
}
