package com.swapit.oopswap.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.swapit.oopswap.domain.model.chat.ChatRoom
import com.swapit.oopswap.ui.navigation.NavItem
import com.swapit.oopswap.ui.shopping.model.calculateTime
import com.swapit.oopswap.ui.theme.BackgroundColor
import com.swapit.oopswap.ui.theme.Gray3
import com.swapit.oopswap.ui.theme.Gray4
import com.swapit.oopswap.ui.theme.Gray5
import com.swapit.oopswap.ui.theme.Paddings
import com.swapit.oopswap.ui.theme.Red
import com.swapit.oopswap.ui.theme.Typography
import com.swapit.oopswap.ui.theme.White

@Composable
fun ChatCard(
    chatCardData: ChatRoom,
    navController: NavHostController,
    chatViewModel: ChatViewModel,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth(),
        colors = CardDefaults.cardColors(BackgroundColor),
        onClick = {
            chatViewModel.fetchChatRoomProduct(chatCardData.chatroomId) {
                chatViewModel.enterChatRoom(chatCardData.chatroomId)
                navController.navigate(NavItem.ChatRoom.screenRoute + "/${chatCardData.chatroomId}")
            }
        },
    ) {
        ChatCardContent(chatCardData = chatCardData)
    }
}

@Composable
fun ChatCardContent(chatCardData: ChatRoom) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Paddings.xlarge, Paddings.large)
                .background(BackgroundColor),
    ) {
        ChatCardUserImageSection(chatCardData)
        ChatCardUserMessageContentSection(chatCardData)
    }
}

@Composable
fun ChatCardUserImageSection(chatCardData: ChatRoom) {
    Box(
        modifier =
            Modifier
                .size(57.dp)
                .clip(CircleShape).background(Gray5),
    ) {
        AsyncImage(
            model = chatCardData.profileImageUrl,
            contentDescription = "유저 사진",
            modifier =
                Modifier
                    .size(52.dp)
                    .clip(CircleShape).align(Alignment.Center),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ChatCardUserMessageContentSection(chatCardData: ChatRoom) {
    val maxUnread = 99
    val convertTime = calculateTime(chatCardData.recentChatTime)
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = chatCardData.nickname,
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
                text = convertTime,
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
                text = chatCardData.recentChat,
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

            if (chatCardData.unReadChatCount.toInt() != 0) {
                Box(
                    modifier =
                        Modifier
                            .size(
                                chatCardData.unReadChatCount.toInt()
                                    .toString().length.dp * 4 + 20.dp,
                                20.dp,
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .background(Red),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text =
                            if (chatCardData.unReadChatCount.toInt() <= maxUnread) {
                                chatCardData.unReadChatCount.toInt().toString()
                            } else {
                                "99+"
                            },
                        color = White,
                        modifier = Modifier.align(Alignment.Center),
                        style = Typography.labelLarge,
                    )
                }
            }
        }
    }
}
