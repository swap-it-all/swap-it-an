package com.example.swap_it.ui.shopping.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swap_it.ui.component.DefaultButton
import com.example.swap_it.ui.component.GrayDefaultButton
import com.example.swap_it.ui.theme.Paddings

@Composable
fun BottomButtonSection(modifier: Modifier){
    GrayDefaultButton(
        text = "바로 스왑 요청하기",
        enabled = true,
        contentPadding = PaddingValues(
            horizontal = 28.dp,
            vertical = Paddings.xlarge
        )
    ) {

    }
    DefaultButton(
        text = "채팅하기",
        enabled = true,
        modifier = modifier.padding(start = Paddings.large),
        contentPadding = PaddingValues(
            horizontal = 58.dp,
            vertical = Paddings.xlarge
        )
    ) {

    }
}