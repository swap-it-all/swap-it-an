package com.example.swapit.ui.shopping.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.ui.component.DefaultButton
import com.example.swapit.ui.component.ModalButton
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Paddings

@Composable
fun BottomButtonSection(navController: NavHostController) {
    ModalButton(
        text = "바로 스왑 요청하기",
        contentPadding =
            PaddingValues(
                horizontal = 28.dp,
                vertical = Paddings.xlarge,
            ),
        containerColor = Gray5,
    ) {
        navController.navigate("MyProductSelection")
    }
    DefaultButton(
        text = "채팅하기",
        enabled = true,
        modifier = Modifier.padding(start = Paddings.large),
        contentPadding =
            PaddingValues(
                horizontal = 58.dp,
                vertical = Paddings.xlarge,
            ),
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun BottomButtonSection() {
    BottomButtonSection(rememberNavController())
}
