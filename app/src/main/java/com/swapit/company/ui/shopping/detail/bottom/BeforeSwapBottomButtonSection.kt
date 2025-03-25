package com.swapit.company.ui.shopping.detail.bottom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.R
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.component.DefaultButton
import com.swapit.company.ui.component.ModalButton
import com.swapit.company.ui.navigation.NavItem
import com.swapit.company.ui.shopping.detail.ShoppingDetailViewModel
import com.swapit.company.ui.theme.Gray5
import com.swapit.company.ui.theme.Paddings

@Composable
fun BeforeSwapBottomButtonSection(
    navController: NavHostController,
    shoppingDetailViewModel: ShoppingDetailViewModel,
    chatViewModel: ChatViewModel,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val horizontalPadding = screenWidthDp.value / 20
    ModalButton(
        text = stringResource(R.string.shopping_detail_swap_request_bottom_button),
        contentPadding =
            PaddingValues(
                horizontal = horizontalPadding.dp,
                vertical = Paddings.xlarge,
            ),
        containerColor = Gray5,
    ) {
        navController.navigate(NavItem.MyProductSelection.screenRoute + "/${shoppingDetailViewModel.goodsId}")
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
        onClick = {
            if (shoppingDetailViewModel.detailContents.trade == null) {
                chatViewModel.initiateChatFlow(shoppingDetailViewModel.goodsId.toLong(), navController)
            } else {
                chatViewModel.initiateChatSwapFlow(shoppingDetailViewModel.detailContents.trade?.tradesId ?: 1, navController)
            }
        },
    )
}
