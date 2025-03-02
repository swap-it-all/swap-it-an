package com.example.swapit.ui.shopping.detail.select

import ShoppingViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.R
import com.example.swapit.domain.repository.MyProductSelectRepository
import com.example.swapit.domain.repository.ShoppingRepository
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProductSelectScreen(
    navController: NavHostController,
    viewModel: MyProductSelectionViewModel,
) {
    var openDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { MyProductAppBar(navController) }
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
            BackButtonAndTextSection(navController)
            ProductListSection(viewModel = viewModel, { openDialog = true })
            DialogSection(openDialog, onClickCancel = { openDialog = false }, navController)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProductAppBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = {
            BackButton(
                modifier = Modifier.padding(start = Paddings.xlarge),
                navController = navController
            )
        },
        title = {},
        colors =
        TopAppBarColors(
            containerColor = BackgroundColor,
            navigationIconContentColor = BackgroundColor,
            actionIconContentColor = BackgroundColor,
            scrolledContainerColor = BackgroundColor,
            titleContentColor = BackgroundColor,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun SwapRequestScreenPreview() {
    MyProductSelectScreen(
        rememberNavController(), MyProductSelectionViewModel(
            MyProductSelectRepository.instance(),
        )
    )
}
