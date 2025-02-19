package com.example.swapit.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Gray5

@Composable
fun NoChatIconSection(contentPadding: PaddingValues) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_no_chat),
                contentDescription = "채팅 없음",
                tint = Gray5,
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(75.dp),
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text("채팅이 없습니다", color = Gray4, textAlign = TextAlign.Center)
        }
    }
}
