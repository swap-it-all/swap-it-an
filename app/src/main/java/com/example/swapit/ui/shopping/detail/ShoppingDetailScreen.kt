package com.example.swapit.ui.shopping.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.swapit.data.datasource.local.model.shopping.TradeStatus
import com.example.swapit.domain.model.product.detail.ProductDetail
import com.example.swapit.ui.chat.ChatViewModel
import com.example.swapit.ui.shopping.detail.select.MyProductSelectViewModel
import com.example.swapit.ui.swap.SwapViewModel
import com.example.swapit.ui.theme.Paddings

@Composable
fun ShoppingDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    shoppingDetailViewModel: ShoppingDetailViewModel,
    myProductSelectViewModel: MyProductSelectViewModel,
    swapViewModel: SwapViewModel,
    chatViewModel: ChatViewModel,
) {
    Box(modifier.fillMaxSize()) {
        DetailContent(navController, shoppingDetailViewModel.detailContents)
        Row(
            modifier =
            Modifier
                .align(Alignment.BottomCenter)
                .padding(Paddings.xlarge, 40.dp),
        ) {
            if (shoppingDetailViewModel.detailContents.trade != null) { // 거래를 누군가와 하고 있음
                if (shoppingDetailViewModel.detailContents.trade!!.isRequester) { // 그게 내가 건거야?
                    if (shoppingDetailViewModel.detailContents.trade!!.status == TradeStatus.INPROGRESS.name) { // 완료 상태면
                        CompleteSwapBottomButtonSection(
                            navController,
                            shoppingDetailViewModel,
                            swapViewModel,
                            chatViewModel = chatViewModel
                        )
                    } else { // 완료 상태 아니면
                        AfterSwapBottomButtonSection(
                            shoppingDetailViewModel,
                            swapViewModel,
                            chatViewModel,
                            navController,
                            shoppingDetailViewModel
                        )
                    }
                } else { // 아님 내가 받은 거야
                    if (shoppingDetailViewModel.detailContents.trade!!.status == TradeStatus.INPROGRESS.name) { // 완료 상태면
                        CompleteSwapBottomButtonSection(
                            navController,
                            shoppingDetailViewModel,
                            swapViewModel,
                            chatViewModel = chatViewModel
                        )
                    } else { // 완료 상태 아니면
                        ReceivedSwapBottomButtonSection(
                            shoppingDetailViewModel,
                            swapViewModel,
                            chatViewModel,
                            navController,
                            shoppingDetailViewModel
                        )
                    }
                }
            } else { // 거래 안하고 있음
                if (myProductSelectViewModel.onSaleProducts.find { it.goodsId == shoppingDetailViewModel.detailContents.goodsId } == null &&
                    myProductSelectViewModel.soldOutProducts.find { it.goodsId == shoppingDetailViewModel.detailContents.goodsId } == null
                ) { // 내 물건이 아니면 기본 버튼 보여줌
                    BeforeSwapBottomButtonSection(
                        navController,
                        shoppingDetailViewModel = shoppingDetailViewModel,
                        chatViewModel = chatViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    navController: NavHostController,
    shoppingDetailData: ProductDetail,
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        ProductImageSection(shoppingDetailData, navController)
        ProductContentSection(shoppingDetailData)
    }
}
