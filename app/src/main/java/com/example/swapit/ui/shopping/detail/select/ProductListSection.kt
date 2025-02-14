package com.example.swapit.ui.shopping.detail.select

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.example.swapit.ui.shopping.ShoppingCard
import com.example.swapit.ui.shopping.productCardData

@Composable
fun ProductListSection(
    openDialog: () -> Unit,
    navController: NavHostController,
) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(4) {
            ShoppingCard(shoppingCardData = productCardData, navController, {
                openDialog()
            })
        }
    }
}
