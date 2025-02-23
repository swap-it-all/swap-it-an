package com.example.swapit.ui.chat.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.data.model.Chat
import com.example.swapit.ui.theme.Gray2
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.White
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatBubble(chat: Chat) {
    val arrangement = if (chat.senderId == 1L) Arrangement.End else Arrangement.Start
    val containerColor = if (chat.senderId == 1L) Primary else Gray5
    val textColor = if (chat.senderId == 1L) White else Gray2
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement,
    ) {
        if (chat.senderId == 1L) {
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BubbleTime(chat: Chat) {
    Text(
        chat.createdAt.format(DateTimeFormatter.ofPattern(stringResource(R.string.chat_bubble_time_format))),
        Modifier.padding(end = Paddings.small),
    )
}

@Composable
fun Bubble(
    chat: Chat,
    containerColor: Color,
    textColor: Color,
) {
    Box(
        modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(containerColor)
                .padding(Paddings.xlarge, 7.dp),
    ) {
        Text(chat.content, color = textColor)
    }
}
