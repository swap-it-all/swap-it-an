package com.swapit.company.ui.shopping.detail.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.R
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.component.ModalButton
import com.swapit.company.ui.shopping.detail.ShoppingDetailViewModel
import com.swapit.company.ui.swap.SwapViewModel
import com.swapit.company.ui.theme.Gray5
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Primary
import com.swapit.company.ui.theme.White

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
        swapViewModel.swapReject(viewModel.detailContents.trade?.tradesId ?: 1)
        navController.navigateUp()
    }
    Spacer(modifier = Modifier.padding(Paddings.small))
    ModalButton(
        text = "스왑 수락하기",
        contentPadding =
            PaddingValues(
                horizontal = horizontalPadding.dp,
                vertical = Paddings.xlarge,
            ),
        containerColor = Gray5,
    ) {
        swapViewModel.swapAccept(viewModel.detailContents.trade?.tradesId ?: 1)
        shoppingDetailViewModel.fetchProductDetail()
    }
    Spacer(modifier = Modifier.padding(Paddings.small))
    TextButton(
        modifier =
            Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Primary)
                .size((horizontalPadding * 2.4).dp),
        onClick = {
            if (shoppingDetailViewModel.detailContents.trade == null) {
                chatViewModel.initiateChatFlow(
                    shoppingDetailViewModel.goodsId.toLong(),
                    navController,
                )
            } else {
                chatViewModel.initiateChatSwapFlow(
                    shoppingDetailViewModel.detailContents.trade!!.tradesId,
                    navController,
                )
            }
        },
        enabled = true,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_chat),
            contentDescription = "채팅 아이콘",
            tint = White,
        )
    }
}
