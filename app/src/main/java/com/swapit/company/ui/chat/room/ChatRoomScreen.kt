package com.swapit.company.ui.chat.room

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.user.UserInfoViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatRoomScreen(
    navController: NavHostController,
    chatRoomId: String,
    chatViewModel: ChatViewModel,
    userInfoViewModel: UserInfoViewModel,
) {
    chatViewModel.fetchChatList(chatRoomId.toLong())
    chatViewModel.enterChatRoom(chatRoomId.toLong())
    DisposableEffect(Unit) {
        onDispose {
            chatViewModel.viewModelScope.launch {
                Log.d("STOMP", "현재 chatList: ${chatViewModel.chatList}")
                chatViewModel.sendReadReceipt() // 읽은 메시지 ID 전송
            }
        }
    }

    Scaffold(
        modifier =
            Modifier
                .background(BackgroundColor)
                .imePadding(),
        topBar = {
            ChatRoomAppBar(navController = navController, chatViewModel)
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
            ChatRoomContent(chats = chatViewModel.chatList, modifier = Modifier.weight(1f), userInfoViewModel = userInfoViewModel)
//            ChatRoomTradeButtonBar(chatViewModel)
            BottomChatBar(viewModel = chatViewModel)
        }
    }
}
