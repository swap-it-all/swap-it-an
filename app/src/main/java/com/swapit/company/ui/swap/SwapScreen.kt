package com.swapit.company.ui.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.swapit.company.ui.alert.AlertViewModel
import com.swapit.company.ui.component.AppBar
import com.swapit.company.ui.component.BottomNavigationBar
import com.swapit.company.ui.theme.BackgroundColor

@Composable
fun SwapScreen(
    navController: NavHostController,
    viewModel: SwapViewModel,
    alertViewModel: AlertViewModel,
) {
    viewModel.fetchReceivedSwap()
    viewModel.fetchSentSwap()

    Scaffold(
        topBar = {
            AppBar(navController = navController, alertCount = alertViewModel.alertList.value.size)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { contentPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .background(BackgroundColor),
        ) {
            ReceivedSwapSection(Modifier, navController, viewModel)
            SentSwapSection(Modifier, navController, viewModel)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SwapScreenPreview() {
    SwapScreen(navController = NavHostController(context = LocalContext.current), viewModel = viewModel(), alertViewModel = viewModel())
}
