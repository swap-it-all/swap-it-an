package com.example.swapit.ui.shopping.detail.select

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.swapit.ui.shopping.ShoppingCard

@Composable
fun ProductListSection(
    myProductSelectionViewModel: MyProductSelectViewModel,
    swapViewModel: SwapProductViewModel,
    targetProductId: Long,
) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        itemsIndexed(
            items = myProductSelectionViewModel.products,
            key = { _, item -> item.goodsId },
        ) { _, item ->
            ShoppingCard(item) {
                swapViewModel.openDialog(item.goodsId)
            }
            DialogSection(
                swapViewModel.dialogStates.value[item.goodsId] ?: false,
                onClickCancel = {
                    swapViewModel.closeDialog(item.goodsId)
                },
                onClickConfirm = {
                    swapViewModel.closeDialog(item.goodsId)
                    swapViewModel.requestedProductId.longValue = item.goodsId
                    swapViewModel.targetProductId.longValue = targetProductId
                    swapViewModel.swapRequest()
                },
                item.imageUrl,
            )
        }
    }
}
