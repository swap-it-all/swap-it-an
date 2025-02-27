package com.example.swapit.ui.shopping.detail.select

import ShoppingViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.domain.repository.ShoppingRepository
import com.example.swapit.ui.theme.BackgroundColor

@Composable
fun MyProductSelectionScreen(
    navController: NavHostController,
    viewModel: ShoppingViewModel,
) {
    var openDialog by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor),
    ) {
        BackButtonAndTextSection(navController)
        ProductListSection(viewModel = viewModel, { openDialog = true })
        DialogSection(openDialog, onClickCancel = { openDialog = false }, navController)
    }
}

@Preview(showBackground = true)
@Composable
fun SwapRequestScreenPreview() {
    MyProductSelectionScreen(rememberNavController(), ShoppingViewModel(ShoppingRepository.instance()))
}
