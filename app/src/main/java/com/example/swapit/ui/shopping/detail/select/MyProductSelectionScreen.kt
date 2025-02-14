package com.example.swapit.ui.shopping.detail.select

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
import com.example.swapit.ui.theme.BackgroundColor

@Composable
fun MyProductSelectionScreen(navController: NavHostController) {
    var openDialog by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor),
    ) {
        BackButtonAndTextSection(navController)
        ProductListSection({ openDialog = true }, navController)
        DialogSection(openDialog, onClickCancel = { openDialog = false }, navController)
    }
}

@Preview(showBackground = true)
@Composable
fun SwapRequestScreenPreview() {
    MyProductSelectionScreen(rememberNavController())
}
