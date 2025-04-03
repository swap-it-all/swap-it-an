package com.swapit.company.ui.chat

import android.util.Log
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
import com.swapit.company.domain.model.chat.ChatRoom
import com.swapit.company.ui.shopping.model.calculateTime
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Gray3
import com.swapit.company.ui.theme.Gray4
import com.swapit.company.ui.theme.Gray5
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Red
import com.swapit.company.ui.theme.Typography
import com.swapit.company.ui.theme.White

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
            chatViewModel.fetchChatRoomProduct(chatCardData.chatroomId) { product ->
                if (product != null && product.goodsId != 1L) {
                    chatViewModel.initiateChatFlow(product.goodsId, navController)
                } else {
                    Log.e("ChatCard", "유효하지 않은 ChatRoomProduct: $product")
                }
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
