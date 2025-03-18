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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

import java.time.LocalDateTime
@Composable
fun ChatRoomContent(
    chats: List<Chat>,
    modifier: Modifier = Modifier,
    userInfoViewModel: UserInfoViewModel,
) {
    val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
    val sortedChats = chats.sortedBy {
        LocalDateTime.parse(it.createdAt, dateTimeFormatter)
    }
    val listState = rememberLazyListState()
    LaunchedEffect(sortedChats.size) {
        if (sortedChats.isNotEmpty()) {
            listState.scrollToItem(sortedChats.size - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier =
        modifier
            .fillMaxWidth()
            .background(BackgroundColor),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = sortedChats.size,
            key = { sortedChats[it].chatsId },
        ) { item ->
            ChatBubble(sortedChats[item], userInfoViewModel = userInfoViewModel)
        }
    }
}

