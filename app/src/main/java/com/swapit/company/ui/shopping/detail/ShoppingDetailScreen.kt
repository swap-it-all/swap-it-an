package com.swapit.company.ui.shopping.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.data.datasource.local.model.shopping.TradeStatus
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.shopping.detail.bottom.AfterSwapBottomButtonSection
import com.swapit.company.ui.shopping.detail.bottom.BeforeSwapBottomButtonSection
import com.swapit.company.ui.shopping.detail.bottom.CompleteSwapBottomButtonSection
import com.swapit.company.ui.shopping.detail.bottom.ReceivedSwapBottomButtonSection
import com.swapit.company.ui.swap.SwapViewModel
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.user.UserInfoViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ShoppingDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    shoppingDetailViewModel: ShoppingDetailViewModel,
    userInfoViewModel: UserInfoViewModel,
    swapViewModel: SwapViewModel,
    chatViewModel: ChatViewModel,
) {
    LaunchedEffect(Unit) {
        shoppingDetailViewModel.fetchProductDetail()
        userInfoViewModel.myUserInfo()
    }

    Box(modifier.fillMaxSize()) {
        DetailContent(navController, shoppingDetailViewModel, userInfoViewModel)
        Row(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(Paddings.xlarge, 40.dp).background(BackgroundColor),
            // todo: 수정
        ) {
            if (shoppingDetailViewModel.detailContents.trade != null) { // 거래를 누군가와 하고 있음
                if (shoppingDetailViewModel.detailContents.trade!!.isRequester) { // 그게 내가 건거야?
                    if (shoppingDetailViewModel.detailContents.trade!!.status == TradeStatus.INPROGRESS.name) { // 완료 상태면
                        CompleteSwapBottomButtonSection(
                            navController,
                            shoppingDetailViewModel,
                            swapViewModel,
                            chatViewModel = chatViewModel,
                        )
                    } else { // 완료 상태 아니면
                        AfterSwapBottomButtonSection(
                            shoppingDetailViewModel,
                            swapViewModel,
                            chatViewModel,
                            navController,
                            shoppingDetailViewModel,
                        )
                    }
                } else { // 아님 내가 받은 거야
                    if (shoppingDetailViewModel.detailContents.trade!!.status == TradeStatus.INPROGRESS.name) { // 완료 상태면
                        CompleteSwapBottomButtonSection(
                            navController,
                            shoppingDetailViewModel,
                            swapViewModel,
                            chatViewModel = chatViewModel,
                        )
                    } else { // 완료 상태 아니면
                        ReceivedSwapBottomButtonSection(
                            shoppingDetailViewModel,
                            swapViewModel,
                            chatViewModel,
                            navController,
                            shoppingDetailViewModel,
                        )
                    }
                }
            } else { // 거래 안하고 있음
                if (userInfoViewModel.userInfo.value?.id != shoppingDetailViewModel.detailContents.user.userId) { // 내 물건이 아니면
                    // 내 물건이 아니면 기본 버튼 보여줌
                    BeforeSwapBottomButtonSection(
                        navController,
                        shoppingDetailViewModel = shoppingDetailViewModel,
                        chatViewModel = chatViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    navController: NavHostController,
    shoppingDetailViewModel: ShoppingDetailViewModel,
    userInfoViewModel: UserInfoViewModel,
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        ProductImageSection(shoppingDetailViewModel, navController, uerInfoViewModel = userInfoViewModel)
        ProductContentSection(shoppingDetailViewModel)
    }
}
