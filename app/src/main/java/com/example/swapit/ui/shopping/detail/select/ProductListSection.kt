package com.example.swapit.ui.shopping.detail.select

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.example.swapit.ui.shopping.ShoppingCard

@Composable
fun ProductListSection(
    myProductSelectionViewModel: MyProductSelectViewModel,
    swapViewModel: SwapProductViewModel,
    targetProductId: Long
) {
    var openDialog by remember { mutableStateOf(false) }
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        itemsIndexed(
            items = myProductSelectionViewModel.products,
            key = { _, item -> item.goodsId },
        ) { _, item ->
            ShoppingCard(item) {
                openDialog = true
            }
            DialogSection(
                openDialog,
                onClickCancel = {
                    openDialog = false
                },
                onClickConfirm = {
                    openDialog = false
                    swapViewModel.requestedProductId.longValue = item.goodsId
                    swapViewModel.targetProductId.longValue = targetProductId
                    swapViewModel.swapRequest()
                },
                item.imageUrl
            )
        }
    }

}
