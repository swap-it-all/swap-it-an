package com.example.swapit.ui.shopping.detail

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.R
import com.example.swapit.data.datasource.remote.dto.request.chat.GoodsIdRequest
import com.example.swapit.domain.repository.ChatRepository
import com.example.swapit.domain.repository.ProductRepository
import com.example.swapit.ui.chat.ChatViewModel
import com.example.swapit.ui.component.DefaultButton
import com.example.swapit.ui.component.ModalButton
import com.example.swapit.ui.navigation.NavItem
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Paddings

@Composable
fun BottomButtonSection(
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
            chatViewModel.createChatRoom(GoodsIdRequest(shoppingDetailViewModel.goodsId.toLong()) )
            chatViewModel.connect()
            chatViewModel.subscribeToChatRoom()
            if(chatViewModel.chatRoomId.longValue!=0L) {
                navController.navigate(NavItem.ChatRoom.screenRoute+"/${chatViewModel.chatRoomId.longValue}")
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun BottomButtonSectionPreview() {
    BottomButtonSection(
        rememberNavController(),
        shoppingDetailViewModel =
            ShoppingDetailViewModel(
                repository = ProductRepository.instance(LocalContext.current),
                _goodsId = "",
            ),
        chatViewModel = ChatViewModel(repository = ChatRepository.instance())
    )
}
