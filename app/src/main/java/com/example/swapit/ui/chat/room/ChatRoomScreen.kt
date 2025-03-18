package com.example.swapit.ui.chat.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.swapit.ui.chat.ChatViewModel
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.user.UserInfoViewModel

@Composable
fun ChatRoomScreen(navController: NavHostController, chatRoomId: String, chatViewModel: ChatViewModel, userInfoViewModel: UserInfoViewModel) {
    chatViewModel.fetchChatList(chatRoomId.toLong())
    chatViewModel.chatRoomId.longValue = chatRoomId.toLong()
    DisposableEffect(Unit) {
        onDispose {
            chatViewModel.sendReadReceipt() // 읽은 메시지 ID 전송
            chatViewModel.disconnect() // 연결 해제
        }
    }
    Scaffold(
        modifier =
        Modifier
            .background(BackgroundColor)
            .imePadding(),
        topBar = {
            ChatRoomAppBar(navController = navController,chatViewModel)
        },
    ) { contentPadding ->
        Column(
            modifier =
            Modifier
                .padding(contentPadding)
                .background(BackgroundColor),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            ChatRoomProduct(chatViewModel)
            ChatRoomContent(chats = chatViewModel.chatList.value, modifier = Modifier.weight(1f), userInfoViewModel = userInfoViewModel)
//            ChatRoomTradeButtonBar(chatViewModel)
            BottomChatBar(viewModel = chatViewModel)
        }
    }
}

