package com.example.swapit.ui.user.setting.withdraw

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swapit.R
import com.example.swapit.ui.alert.AlertAppBar
import com.example.swapit.ui.auth.LoginViewModel
import com.example.swapit.ui.chat.room.ChatField
import com.example.swapit.ui.component.DefaultButton
import com.example.swapit.ui.component.ModalButton
import com.example.swapit.ui.navigation.NavItem
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Gray6
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Red
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.user.UserInfoViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WithdrawScreen(
    navController: NavHostController,
    userInfoViewModel: UserInfoViewModel,
) {
    Scaffold(
        topBar = { WithdrawAppBar(navController) },
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding).background(BackgroundColor)) {
            Text(
                "${userInfoViewModel.userInfo.value?.nickname}님과 이별인가요? 너무 아쉽네요..",
                style = Typography.titleLarge,
                modifier = Modifier.padding(Paddings.xlarge),
            )
            Text("계정을 삭제하면 별점, 게시글, 채팅 등 모든 활동 정보가 삭제됩니다.",style = Typography.bodyMedium,
                modifier = Modifier.padding(Paddings.xlarge),)
            Spacer(modifier = Modifier.height(16.dp))
            Text("${userInfoViewModel.userInfo.value?.nickname}님이 저희 스왑잇을 떠나시려는 이유를 알 수 있을까요?",style = Typography.titleLarge,
                modifier = Modifier.padding(Paddings.xlarge),)
            WithdrawDropdownMenu(viewModel = userInfoViewModel)
            if (userInfoViewModel.selectedText.value == "기타") {
                ChatField(
                    modifier = Modifier.padding(Paddings.xlarge),
                    message = userInfoViewModel.etcText.value,
                    onValueChange = { userInfoViewModel.etcText.value = it },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                val configuration = LocalConfiguration.current
                val screenWidthDp = configuration.screenWidthDp.dp
                val horizontalPadding = screenWidthDp.value / 20
                ModalButton(
                    text = "취소하기",
                    contentPadding =
                    PaddingValues(
                        horizontal = horizontalPadding.dp*2,
                        vertical = Paddings.xlarge,
                    ),
                    containerColor = Gray5,
                ) {
                    navController.navigateUp()
                }
                DefaultButton(
                    text = "제출하기",
                    enabled = true,
                    modifier = Modifier.padding(start = Paddings.large),
                    contentPadding =
                    PaddingValues(
                        horizontal = horizontalPadding.dp*2,
                        vertical = Paddings.xlarge,
                    ),
                    onClick = {
                    },
                )
            }
        }
    }
}

