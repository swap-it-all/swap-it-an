package com.example.swapit.ui.user.setting

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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swapit.ui.alert.AlertAppBar
import com.example.swapit.ui.auth.LoginViewModel
import com.example.swapit.ui.navigation.NavItem
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Gray6
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Red
import com.example.swapit.ui.theme.Typography

@Composable
fun SettingScreen(
    navController: NavHostController,
    initialState: Boolean = false,
    loginViewModel: LoginViewModel,
) {
    var isNotificationEnabled by remember { mutableStateOf(initialState) }
    Scaffold(
        topBar = { AlertAppBar(navController) },
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    "알림 수신",
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(Paddings.xlarge),
                )
                Switch(
                    modifier = Modifier.padding(Paddings.xlarge),
                    colors =
                        SwitchDefaults.colors(
                            checkedThumbColor = Primary,
                            checkedTrackColor = Gray6,
                            uncheckedThumbColor = Gray4,
                            uncheckedTrackColor = Gray6,
                            disabledUncheckedBorderColor = Gray6,
                        ),
                    checked = isNotificationEnabled,
                    onCheckedChange = {
                        isNotificationEnabled = it
                        // todo : 알림 설정 변경 로직 구현
                    },
                )
            }
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
                    navController.navigate(NavItem.Login.screenRoute)
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
                onClick = {},
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
