package com.example.swap_it.ui.shopping.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.ui.component.DefaultButton
import com.example.swap_it.ui.component.ModalButton
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Gray5
import com.example.swap_it.ui.theme.Paddings

@Composable
fun BottomButtonSection(modifier: Modifier){
    ModalButton (
        text = "바로 스왑 요청하기",
        contentPadding = PaddingValues(
            horizontal = 28.dp,
            vertical = Paddings.xlarge
        ),
        containerColor = Gray5
    ){

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
@Preview(showBackground = true)
@Composable
fun BottomButtonSection(){
    BottomButtonSection(Modifier)
}