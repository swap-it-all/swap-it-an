package com.example.swapit.ui.user.setting.withdraw

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.ui.theme.Black

@Composable
fun WithdrawDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { expanded = !expanded },
            border = BorderStroke(
                1.dp,
                Black
            )
        ) {
            Row (verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween){
                Text("선택해주세요.")
                Icon(painterResource(R.drawable.ic_caret_down),contentDescription = "Dropdown Menu")
            }

        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("너무 많이 이용해요") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("거래하고 싶은 물품이 없어요") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("물품이 안 팔려요") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("비매너 사용자를 만났어요") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("알림이 너무 많이 와요") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("새 계정을 만들고 싶어요") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("기타") },
                onClick = { /* Do something... */ }
            )
        }
    }
}