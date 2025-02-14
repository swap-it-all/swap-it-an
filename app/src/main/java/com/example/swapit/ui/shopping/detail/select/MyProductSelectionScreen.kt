package com.example.swapit.ui.shopping.detail.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.R
import com.example.swapit.data.model.ShoppingCardData
import com.example.swapit.ui.component.AlertDialog
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.shopping.ShoppingCard
import com.example.swapit.ui.shopping.productCardData
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography


@Composable
fun MyProductSelectionScreen(navController: NavHostController) {
    var openDialog by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
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

