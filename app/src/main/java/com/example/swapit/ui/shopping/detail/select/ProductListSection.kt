package com.example.swapit.ui.shopping.detail.select

import ShoppingViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.swapit.ui.shopping.ShoppingCard

@Composable
fun ProductListSection(
    viewModel: ShoppingViewModel,
    openDialog: () -> Unit,
) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(viewModel.products.size) {
            ShoppingCard(viewModel.products[it], {
                openDialog()
            })
        }
    }
}
