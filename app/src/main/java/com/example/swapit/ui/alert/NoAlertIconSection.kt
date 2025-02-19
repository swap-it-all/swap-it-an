package com.example.swapit.ui.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Paddings

@Composable
fun NoAlertIconSection() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(BackgroundColor),
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = Paddings.xextra)
                    .background(BackgroundColor),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_no_alert),
                contentDescription = "알림 없음",
                tint = Gray5,
                modifier =
                    Modifier
                        .size(75.dp),
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text("알림이 없습니다", color = Gray4, textAlign = TextAlign.Center)
        }
    }
}
