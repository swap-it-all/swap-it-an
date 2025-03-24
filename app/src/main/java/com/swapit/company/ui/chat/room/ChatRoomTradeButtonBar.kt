package com.swapit.company.ui.chat.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.swapit.company.ui.component.CategoryButton
import com.swapit.company.ui.swap.SwapViewModel
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.White

@Composable
fun ChatRoomTradeButtonBar(swapViewModel: SwapViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.small, vertical = Paddings.small)
                .background(White),
    ) {
        CategoryButton(
            text = "스왑 요청하기",
            isSelected = true,
            onClick = { swapViewModel.swapRequest() },
        )
    }
}
