package com.example.swapit.ui.shopping.detail.select

import ShoppingViewModel
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.swapit.ui.navigation.NavItem
import com.example.swapit.ui.shopping.ShoppingCard

@Composable
fun ProductListSection(
    viewModel: MyProductSelectionViewModel,
    openDialog: () -> Unit,
) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        itemsIndexed(
            items = viewModel.products,
            key = { _, item -> item.goodsId },
        ) { _, item ->
            ShoppingCard(item) {
                openDialog()
            }
        }
    }
}
