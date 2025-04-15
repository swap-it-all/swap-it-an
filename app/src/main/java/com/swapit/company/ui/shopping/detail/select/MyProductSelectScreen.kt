package com.swapit.company.ui.shopping.detail.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.swapit.company.domain.repository.ProductRepository
import com.swapit.company.domain.repository.SwapRepository
import com.swapit.company.ui.component.BackButton
import com.swapit.company.ui.swap.SwapViewModel
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProductSelectScreen(
    navController: NavHostController,
    viewModel: MyProductSelectViewModel,
    targetProductId: Long,
) {
    Scaffold(
        topBar = { MyProductAppBar(navController) },
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
            TextSection(navController)
            ProductListSection(
                myProductSelectionViewModel = viewModel,
                swapViewModel = SwapViewModel(repository = SwapRepository.instance()),
                targetProductId = targetProductId,
                navController = navController,
            )
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
                navController = navController,
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
        rememberNavController(),
        viewModel = MyProductSelectViewModel(repository = ProductRepository.instance(LocalContext.current)),
        targetProductId = 0,
    )
}
