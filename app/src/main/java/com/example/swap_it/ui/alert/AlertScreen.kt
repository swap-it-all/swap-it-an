package com.example.swap_it.ui.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.ui.component.BackButton
import com.example.swap_it.ui.theme.BackgroundColor
import com.example.swap_it.ui.theme.Black
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.Typography


@Composable
fun AlertScreen(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Spacer(Modifier.padding(Paddings.xlarge))
        BackButton(Modifier.padding(Paddings.largeExtra), navController, Black)
        Text(
            stringResource(R.string.alert_alert),
            style = Typography.titleLarge,
            modifier = Modifier.padding(Paddings.xlarge)
        )
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(4) {
                AlertCard(alertCardData = alertCardData)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    var navController = rememberNavController()
    SwapitTheme {
        AlertScreen(navController)
    }
}