package com.swapit.company.ui.user.setting.withdraw

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.swapit.company.R
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Black
import com.swapit.company.ui.theme.Gray2
import com.swapit.company.ui.theme.Typography
import com.swapit.company.ui.user.UserInfoViewModel

@Composable
fun WithdrawDropdownMenu(viewModel: UserInfoViewModel) {
    Box {
        TextButton(
            colors =
                ButtonDefaults.buttonColors(
                    BackgroundColor,
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            onClick = { viewModel.expanded.value = !viewModel.expanded.value },
            border =
                BorderStroke(
                    1.dp,
                    Gray2,
                ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(viewModel.selectedText.value, style = Typography.bodyMedium, color = Black)
                Icon(
                    painterResource(R.drawable.ic_caret_down),
                    contentDescription = "Dropdown Menu",
                )
            }
        }
        DropdownMenu(
            containerColor = BackgroundColor,
            expanded = viewModel.expanded.value,
            onDismissRequest = { viewModel.expanded.value = false },
        ) {
            DropdownMenuItem(
                text = { Text("너무 많이 이용해요") },
                onClick = {
                    viewModel.selectedText.value = "너무 많이 이용해요"
                    viewModel.expanded.value = false
                },
            )
            DropdownMenuItem(
                text = { Text("거래하고 싶은 물품이 없어요") },
                onClick = {
                    viewModel.selectedText.value = "거래하고 싶은 물품이 없어요"
                    viewModel.expanded.value = false
                },
            )
            DropdownMenuItem(
                text = { Text("물품이 안 팔려요") },
                onClick = {
                    viewModel.selectedText.value = "물품이 안 팔려요"
                    viewModel.expanded.value = false
                },
            )
            DropdownMenuItem(
                text = { Text("비매너 사용자를 만났어요") },
                onClick = {
                    viewModel.selectedText.value = "비매너 사용자를 만났어요"
                    viewModel.expanded.value = false
                },
            )
            DropdownMenuItem(
                text = { Text("알림이 너무 많이 와요") },
                onClick = {
                    viewModel.selectedText.value = "알림이 너무 많이 와요"
                    viewModel.expanded.value = false
                },
            )
            DropdownMenuItem(
                text = { Text("새 계정을 만들고 싶어요") },
                onClick = {
                    viewModel.selectedText.value = "새 계정을 만들고 싶어요"
                    viewModel.expanded.value = false
                },
            )
            DropdownMenuItem(
                text = { Text("기타") },
                onClick = {
                    viewModel.selectedText.value = "기타"
                    viewModel.expanded.value = false
                },
            )
        }
    }
}
