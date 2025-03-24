package com.swapit.company.ui.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.swapit.company.R
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.SwapitTheme
import com.swapit.company.ui.theme.Typography

@Composable
fun AlertScreen(navController: NavHostController, viewModel: AlertViewModel) {
    viewModel.fetchAlertList()
    Scaffold(
        topBar = { AlertAppBar(navController) },
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
            Text(
                stringResource(R.string.alert_alert),
                style = Typography.titleLarge,
                modifier = Modifier.padding(Paddings.xlarge),
            )
            if (viewModel.alertList.value.isEmpty()) {
                NoAlertIconSection()
            }
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(
                    viewModel.alertList.value.size,
                    key = { index ->
                        viewModel.alertList.value[index].notificationsId
                    }) { index ->
                    AlertCard(alertCardData = viewModel.alertList.value[index])
                }
            }
        }
    }
}

