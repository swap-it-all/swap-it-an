package com.example.swap_it.ui.chat_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

class ChatListScreen {
    @Composable
    fun ChatListScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
        ) {
            Text(
                text = "채팅",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }


}