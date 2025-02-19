package com.example.swapit.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.swapit.data.model.ChatCardData
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Red
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

val _chatCardData =
    ChatCardData(
        userImageUri = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
        userName = "홍길동",
        lastMessage = "안녕하세요",
        lastMessageTime = "오전 10:30",
        unreadMessageCount = 13,
    )

@Composable
fun ChatCard(chatCardData: ChatCardData) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth(),
        colors = CardDefaults.cardColors(BackgroundColor),
    ) {
        ChatCardContent(chatCardData = _chatCardData)
    }
}

@Composable
fun ChatCardContent(chatCardData: ChatCardData) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Paddings.xlarge, Paddings.large)
                .background(BackgroundColor),
    ) {
        CharCardUserImageSection(chatCardData)
        ChatCardUserMessageComtentSection(chatCardData)
    }
}

@Composable
fun CharCardUserImageSection(chatCardData: ChatCardData) {
    AsyncImage(
        model = chatCardData.userImageUri,
        contentDescription = "유저 사진",
        modifier =
            Modifier
                .size(52.dp)
                .clip(CircleShape),
    )
}

@Composable
fun ChatCardUserMessageComtentSection(chatCardData: ChatCardData) {
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = chatCardData.userName,
                modifier =
                    Modifier.padding(
                        Paddings.large,
                        Paddings.small,
                        Paddings.none,
                        Paddings.xsmall,
                    ),
                style = Typography.titleMedium,
            )
            Text(
                text = chatCardData.lastMessageTime,
                color = Gray4,
                style = Typography.bodySmall,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = chatCardData.lastMessage,
                modifier =
                    Modifier.padding(
                        Paddings.large,
                        Paddings.xsmall,
                        Paddings.none,
                        Paddings.small,
                    ),
                style = Typography.bodySmall,
                color = Gray3,
            )
            Box(
                modifier =
                    Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Red),
            ) {
                Text(
                    text = chatCardData.unreadMessageCount.toString(),
                    color = White,
                    modifier = Modifier.align(Alignment.Center),
                    style = Typography.labelLarge,
                )
            }
        }
    }
}
