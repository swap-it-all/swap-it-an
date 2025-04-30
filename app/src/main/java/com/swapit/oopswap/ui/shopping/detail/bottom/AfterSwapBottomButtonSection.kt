package com.swapit.oopswap.ui.shopping.detail.bottom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.oopswap.R
import com.swapit.oopswap.ui.chat.ChatViewModel
import com.swapit.oopswap.ui.component.DefaultButton
import com.swapit.oopswap.ui.component.ModalButton
import com.swapit.oopswap.ui.shopping.detail.ShoppingDetailViewModel
import com.swapit.oopswap.ui.swap.SwapViewModel
import com.swapit.oopswap.ui.theme.Gray5
import com.swapit.oopswap.ui.theme.Paddings

@Composable
fun AfterSwapBottomButtonSection(
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
        text = "스왑 요청 취소하기",
        contentPadding =
            PaddingValues(
                horizontal = horizontalPadding.dp,
                vertical = Paddings.xlarge,
            ),
        containerColor = Gray5,
    ) {
        swapViewModel.swapCancel(viewModel.detailContents.trade!!.tradesId)
        navController.navigateUp()
    }
    DefaultButton(
        text = stringResource(R.string.shopping_detail_chat_bottom_button),
        enabled = true,
        modifier = Modifier.padding(start = Paddings.large),
        contentPadding =
            PaddingValues(
                horizontal = horizontalPadding.dp * 2,
                vertical = Paddings.xlarge,
            ),
    ) {
        if (shoppingDetailViewModel.detailContents.trade == null) {
            chatViewModel.initiateChatFlow(shoppingDetailViewModel.goodsId.toLong(), navController)
        } else {
            chatViewModel.initiateChatSwapFlow(shoppingDetailViewModel.detailContents.trade?.tradesId ?: 1, navController)
        }
    }
}
