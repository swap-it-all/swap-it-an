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
import com.example.swapit.domain.repository.ChatRepository
import com.example.swapit.ui.chat.ChatViewModel
import com.example.swapit.ui.theme.BackgroundColor
import java.time.LocalDateTime

@Composable
fun ChatRoomScreen(navController: NavHostController, chatRoomId: String, viewModel: ChatViewModel) {
    viewModel.fetchChatList(chatRoomId.toLong())
    viewModel.chatRoomId.longValue = chatRoomId.toLong()
    Scaffold(
        modifier =
        Modifier
            .background(BackgroundColor)
            .imePadding(),
        topBar = {
            ChatRoomAppBar(navController = navController,viewModel)
        },
    ) { contentPadding ->
        Column(
            modifier =
            Modifier
                .padding(contentPadding)
                .background(BackgroundColor),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            ChatRoomProduct(viewModel)
            ChatRoomContent(chats = viewModel.chatList.value, modifier = Modifier.weight(1f))
            BottomChatBar(viewModel = viewModel)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ChatRoomScreenPreview() {
    ChatRoomScreen(
        navController = NavHostController(LocalContext.current),
        "",
        viewModel = ChatViewModel(repository = ChatRepository.instance())
    )
}
