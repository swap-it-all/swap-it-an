package com.swapit.company.ui.user.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.ui.alert.AlertAppBar
import com.swapit.company.ui.auth.LoginViewModel
import com.swapit.company.ui.navigation.NavItem
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Red
import com.swapit.company.ui.theme.Typography

@Composable
fun SettingScreen(
    navController: NavHostController,
    initialState: Boolean = false,
    loginViewModel: LoginViewModel,
) {
    var isNotificationEnabled by remember { mutableStateOf(initialState) }

    LaunchedEffect(loginViewModel.isLoggedIn.collectAsState().value) {
        if (!loginViewModel.isLoggedIn.value) {
            navController.navigate(NavItem.Login.screenRoute) {
                popUpTo(NavItem.Setting.screenRoute) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = { AlertAppBar(navController) },
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
//            Row(
//                Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//            ) {
//                Text(
//                    "알림 수신",
//                    style = Typography.titleLarge,
//                    modifier = Modifier.padding(Paddings.xlarge),
//                )
//                Switch(
//                    modifier = Modifier.padding(Paddings.xlarge),
//                    colors =
//                        SwitchDefaults.colors(
//                            checkedThumbColor = Primary,
//                            checkedTrackColor = Gray6,
//                            uncheckedThumbColor = Gray4,
//                            uncheckedTrackColor = Gray6,
//                            disabledUncheckedBorderColor = Gray6,
//                        ),
//                    checked = isNotificationEnabled,
//                    onCheckedChange = {
//                        isNotificationEnabled = it
//                        // todo : 알림 설정 변경 로직 구현
//                    },
//                )
//            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    "버전 정보",
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(Paddings.xlarge),
                )
                Text(
                    "1.0",
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(Paddings.xlarge),
                )
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Card(
                colors = CardDefaults.cardColors(BackgroundColor),
                onClick = {
                    loginViewModel.logout()
                },
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "로그 아웃",
                        style = Typography.titleLarge,
                        modifier = Modifier.padding(Paddings.xlarge),
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Card(
                colors = CardDefaults.cardColors(BackgroundColor),
                onClick = {
                    navController.navigate(NavItem.Withdraw.screenRoute)
                },
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "회원 탈퇴",
                        style = Typography.titleLarge,
                        color = Red,
                        modifier = Modifier.padding(Paddings.xlarge),
                    )
                }
            }
        }
    }
}
