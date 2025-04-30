package com.swapit.oopswap.ui.alert

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
import androidx.navigation.NavHostController
import com.swapit.oopswap.R
import com.swapit.oopswap.ui.theme.BackgroundColor
import com.swapit.oopswap.ui.theme.Paddings
import com.swapit.oopswap.ui.theme.Typography

@Composable
fun AlertScreen(
    navController: NavHostController,
    viewModel: AlertViewModel,
) {
    viewModel.fetchAlertList()
    val groupedAlerts =
        viewModel.alertList.value
            .groupBy { it.type to it.relatedData } // 알림 타입 + 관련 아이디 기준으로 그룹화
            .mapValues { (_, alerts) -> alerts.first() } // 같은 그룹에서 첫 번째 항목만 사용
            .values.toList()
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
                    groupedAlerts.size,
                    key = { index -> groupedAlerts[index].notificationsId },
                ) { index ->
                    AlertCard(
                        alertViewModel = viewModel,
                        alertCardData = groupedAlerts[index],
                        navController = navController,
                    )
                }
            }
        }
    }
}
