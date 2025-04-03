package com.swapit.company.ui.user.setting.withdraw

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.ui.auth.LoginViewModel
import com.swapit.company.ui.chat.room.ChatField
import com.swapit.company.ui.component.DefaultButton
import com.swapit.company.ui.component.ModalButton
import com.swapit.company.ui.navigation.NavItem
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Gray5
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Typography
import com.swapit.company.ui.user.UserInfoViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WithdrawScreen(
    navController: NavHostController,
    userInfoViewModel: UserInfoViewModel,
    loginViewModel: LoginViewModel,
) {
    Scaffold(
        topBar = { WithdrawAppBar(navController) },
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
            Text(
                "${userInfoViewModel.userInfo.value?.nickname}님과 이별인가요? 너무 아쉽네요..",
                style = Typography.titleLarge,
                modifier = Modifier.padding(Paddings.xlarge),
            )
            Text(
                "계정을 삭제하면 별점, 게시글, 채팅 등 모든 활동 정보가 삭제됩니다.",
                style = Typography.bodyMedium,
                modifier = Modifier.padding(Paddings.xlarge),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "${userInfoViewModel.userInfo.value?.nickname}님이 저희 스왑잇을 떠나시려는 이유를 알 수 있을까요?",
                style = Typography.titleLarge,
                modifier = Modifier.padding(Paddings.xlarge),
            )
            WithdrawDropdownMenu(viewModel = userInfoViewModel)
            if (userInfoViewModel.selectedText.value == "기타") {
                ChatField(
                    modifier = Modifier.padding(Paddings.xlarge),
                    message = userInfoViewModel.etcText.value,
                    onValueChange = { userInfoViewModel.etcText.value = it },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                val configuration = LocalConfiguration.current
                val screenWidthDp = configuration.screenWidthDp.dp
                val horizontalPadding = screenWidthDp.value / 20
                ModalButton(
                    text = "취소하기",
                    contentPadding =
                        PaddingValues(
                            horizontal = horizontalPadding.dp * 2,
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
                            horizontal = horizontalPadding.dp * 2,
                            vertical = Paddings.xlarge,
                        ),
                    onClick = {
                        Log.d("asdfasdfasdf", "asdfdasfsadfsdafasdfasfsdafsadfasdf")
                        if (userInfoViewModel.selectedText.value == "기타") {
                            loginViewModel.deleteAccount(userInfoViewModel.etcText.value) {
                                navController.navigate(NavItem.Login.screenRoute)
                            }
                        } else {
                            loginViewModel.deleteAccount(userInfoViewModel.selectedText.value) {
                                navController.navigate(NavItem.Login.screenRoute)
                            }
                        }
                    },
                )
            }
        }
    }
}
