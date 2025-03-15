package com.example.swapit.ui.chat.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.domain.model.chat.Chat
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.user.UserInfoViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ChatRoomContent(
    chats: List<Chat>,
    modifier: Modifier = Modifier,
    userInfoViewModel: UserInfoViewModel,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxWidth()
                .background(BackgroundColor),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = chats.size,
            key = { chats[it].chatsId },
        ) { item ->
            ChatBubble(chats[item], userInfoViewModel = userInfoViewModel)
        }
    }
}
