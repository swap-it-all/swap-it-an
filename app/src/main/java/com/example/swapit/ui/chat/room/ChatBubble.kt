package com.example.swapit.ui.chat.room

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.data.datasource.local.model.chat.ChatType
import com.example.swapit.domain.model.chat.Chat
import com.example.swapit.ui.shopping.model.calculateTime
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Gray2
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.White
import com.example.swapit.ui.user.UserInfoViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ChatBubble(chat: Chat, userInfoViewModel: UserInfoViewModel) {
    userInfoViewModel.myUserInfo()
    val userInfo by userInfoViewModel.userInfo.collectAsState()
    val arrangement = if (chat.senderId == userInfo?.id) Arrangement.End else Arrangement.Start
    val containerColor = if (chat.senderId == userInfo?.id) Primary else Gray5
    val textColor = if (chat.senderId == userInfo?.id) White else Gray2
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement,
    ) {
        if (chat.senderId == userInfo?.id) {
            Row(verticalAlignment = Alignment.Bottom) {
                BubbleTime(chat)
                Bubble(chat, containerColor, textColor)
            }
        } else {
            Row(verticalAlignment = Alignment.Bottom) {
                Bubble(chat, containerColor, textColor)
                BubbleTime(chat)
            }
        }
    }
}

@Composable
fun BubbleTime(chat: Chat) {
    val convertTime = calculateTime(chat.createdAt)
    Text(
        convertTime,
        Modifier.padding(end = Paddings.small),
    )
}

@Composable
fun Bubble(
    chat: Chat,
    containerColor: Color,
    textColor: Color,
) {
    if (chat.chatType == ChatType.TALK.name){
        Box(
            modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(containerColor)
                .padding(Paddings.xlarge, 7.dp),
        ) {
            Text(chat.content, color = textColor)
        }
    } else if (chat.chatType == ChatType.REQUEST.name) {
        Box(
            modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(BackgroundColor)
                .border(BorderStroke(1.dp, containerColor), RoundedCornerShape(16.dp)))
        {
            Text("스왑을 요청했어요!\n" +
                    "\n" +
                    "물건 : ${chat.requesterGoods!!.title}\n" +
                    "스와퍼 : ${chat.requesterGoods!!.requesterNickname}",)
        }
    } else if (chat.chatType == ChatType.CANCEL.name) {
        Box(
            modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(BackgroundColor)
                .border(BorderStroke(1.dp, containerColor), RoundedCornerShape(16.dp)))
        {
            Text("스왑을 취소했어요!\n" +
                    "\n" +
                    "물건 : ${chat.requesterGoods!!.title}\n" +
                    "스와퍼 : ${chat.requesterGoods!!.requesterNickname}",)
        }
    } else if (chat.chatType == ChatType.CANCEL.name) {
        Box(
            modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(BackgroundColor)
                .border(BorderStroke(1.dp, containerColor), RoundedCornerShape(16.dp)))
        {
            Text("스왑을 수락했어요!\n" +
                    "\n" +
                    "물건 : ${chat.requesterGoods!!.title}\n" +
                    "스와퍼 : ${chat.requesterGoods!!.requesterNickname}",)
        }
    } else if (chat.chatType == ChatType.CANCEL.name) {
        Box(
            modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(BackgroundColor)
                .border(BorderStroke(1.dp, containerColor), RoundedCornerShape(16.dp)))
        {
            Text("스왑을 거절했어요!\n" +
                    "\n" +
                    "물건 : ${chat.requesterGoods!!.title}\n" +
                    "스와퍼 : ${chat.requesterGoods!!.requesterNickname}",)
        }
    } else if (chat.chatType == ChatType.CANCEL.name) {
        Box(
            modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(BackgroundColor)
                .border(BorderStroke(1.dp, containerColor), RoundedCornerShape(16.dp)))
        {
            Text("스왑을 완료했어요!\n" +
                    "\n" +
                    "물건 : ${chat.requesterGoods!!.title}\n" +
                    "스와퍼 : ${chat.requesterGoods!!.requesterNickname}",)
        }
    }

}
