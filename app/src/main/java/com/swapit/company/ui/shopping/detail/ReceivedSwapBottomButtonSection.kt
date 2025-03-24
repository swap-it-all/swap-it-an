package com.swapit.company.ui.shopping.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.R
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.component.ModalButton
import com.swapit.company.ui.swap.SwapViewModel
import com.swapit.company.ui.theme.Gray5
import com.swapit.company.ui.theme.Paddings

@Composable
fun ReceivedSwapBottomButtonSection(
    viewModel: ShoppingDetailViewModel,
    swapViewModel: SwapViewModel,
    chatViewModel: ChatViewModel,
    navController: NavHostController,
    shoppingDetailViewModel: ShoppingDetailViewModel,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val horizontalPadding = screenWidthDp.value / 20
    ModalButton(
        text = "스왑 거절하기",
        contentPadding =
            PaddingValues(
                horizontal = horizontalPadding.dp,
                vertical = Paddings.xlarge,
            ),
        containerColor = Gray5,
    ) {
        swapViewModel.swapReject(viewModel.detailContents.trade!!.tradesId)
        navController.navigateUp()
    }
    ModalButton(
        text = "스왑 수락하기",
        contentPadding =
            PaddingValues(
                horizontal = horizontalPadding.dp,
                vertical = Paddings.xlarge,
            ),
        containerColor = Gray5,
    ) {
        swapViewModel.swapAccept(viewModel.detailContents.trade!!.tradesId)
    }
    TextButton(
        onClick = {
            if (shoppingDetailViewModel.detailContents.trade == null) {
                chatViewModel.initiateChatFlow(shoppingDetailViewModel.goodsId.toLong(), navController)
            } else {
                chatViewModel.initiateChatSwapFlow(shoppingDetailViewModel.detailContents.trade!!.tradesId, navController)
            }
        },
        enabled = true,
        modifier = Modifier.padding(start = Paddings.large),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_chat),
            contentDescription = "채팅 아이콘",
        )
    }
}
