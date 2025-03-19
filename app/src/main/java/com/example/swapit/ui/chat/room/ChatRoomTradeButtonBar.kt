package com.example.swapit.ui.chat.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.swapit.ui.component.CategoryButton
import com.example.swapit.ui.swap.SwapViewModel
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.White

@Composable
fun ChatRoomTradeButtonBar(swapViewModel: SwapViewModel)  {
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
//        CategoryButton(
//            text = "스왑 취소하기",
//            isSelected = true,
//            onClick = { swapViewModel.swapCancel() },
//        )
//        CategoryButton(
//            text = "스왑 수락하기",
//            isSelected = true,
//            onClick = { swapViewModel.swapAccept() },
//        )
//        CategoryButton(
//            text = "스왑 거절하기",
//            isSelected = true,
//            onClick = { swapViewModel.swapReject() },
//        )
//        CategoryButton(
//            text = "스왑 완료하기",
//            isSelected = true,
//            onClick = { swapViewModel.swapComplete() },
//        )
    }
}
