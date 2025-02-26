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
import com.example.swapit.ui.theme.BackgroundColor
import java.time.format.DateTimeFormatter

@Composable
fun ChatRoomContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxWidth()
                .background(BackgroundColor),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                chats[0].createdAt.format(DateTimeFormatter.ofPattern(stringResource(R.string.chat_room_time_format))).toString(),
            ) // todo: 시간 상태 빈 값으로 만들어 놓고, 바뀌면 뜨게 하기
        }
        items(
            count = chats.size,
            key = { chats[it].chatsId },
        ) { item ->
            ChatBubble(chats[item])
        }
    }
}
