package com.swapit.company.ui.chat.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.swapit.company.R
import com.swapit.company.data.datasource.remote.dto.request.chat.ChatRequest
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.theme.Black
import com.swapit.company.ui.theme.Gray4
import com.swapit.company.ui.theme.Gray6
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Typography
import com.swapit.company.ui.theme.White

@Composable
fun BottomChatBar(viewModel: ChatViewModel) {
    var message by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.small, vertical = Paddings.small)
                .background(White),
    ) {
        ChatField(
            modifier = Modifier.weight(1f),
            message = message,
            onValueChange = { message = it },
            onValueChanged = {
                viewModel.sendMessage(
                    ChatRequest(
                        "TALK",
                        message,
                        null,
                    ),
                    onSuccess = { viewModel.fetchChatList(viewModel.chatRoomId.longValue) },
                )
                message = ""
            },
        )
        IconButton(onClick = {
            viewModel.sendMessage(
                ChatRequest(
                    "TALK",
                    message,
                    null,
                ),
                onSuccess = { viewModel.fetchChatList(viewModel.chatRoomId.longValue) },
            )
            message = ""
            viewModel.fetchChatList(viewModel.chatRoomId.longValue)
        }) {
            Box(
                modifier =
                    Modifier
                        .clip(CircleShape)
                        .size(32.dp)
                        .background(Gray6),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_up),
                    contentDescription = "보내기",
                    tint = Gray4,
                )
            }
        }
    }
}

@Composable
fun ChatField(
    modifier: Modifier = Modifier,
    message: String,
    onValueChange: (String) -> Unit = {},
    onValueChanged: (String) -> Unit = {},
) {
    BasicTextField(
        value = message,
        onValueChange = onValueChange,
        textStyle = Typography.bodyMedium.copy(color = Black),
        maxLines = 3,
        keyboardOptions =
            KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send,
            ),
        keyboardActions =
            KeyboardActions(
                onSend = {
                    onValueChanged
                },
                onDone = {
                    onValueChanged
                },
            ),
        modifier =
            modifier
                .heightIn(min = 32.dp)
                .background(Gray6, shape = RoundedCornerShape(32.dp))
                .padding(horizontal = 16.dp),
        decorationBox = { innerTextField ->
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (message.isEmpty()) {
                        Text(
                            stringResource(R.string.chat_write_message),
                            style = Typography.bodySmall,
                            color = Gray4,
                        )
                    }
                    innerTextField()
                }
            }
        },
    )
}
