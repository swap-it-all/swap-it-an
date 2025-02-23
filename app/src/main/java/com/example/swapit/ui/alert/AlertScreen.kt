package com.example.swapit.ui.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.R
import com.example.swapit.ui.chat._chatCardData
import com.example.swapit.ui.chat.room.ChatRoomAppBar
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.component.MenuButton
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.SwapitTheme
import com.example.swapit.ui.theme.Typography

@Composable
fun AlertScreen(navController: NavHostController) {
    val alertCardDataList = 1
    Scaffold (
        topBar = {AlertAppBar(navController)}
    ){contentPadding ->
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
            if (alertCardDataList == 0) {
                NoAlertIconSection()
            }
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(4) {
                    AlertCard(alertCardData = alertCardData)
                }
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
