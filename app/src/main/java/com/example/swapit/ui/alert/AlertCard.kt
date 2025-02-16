package com.example.swapit.ui.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.data.model.AlertCardData
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

@Composable
fun AlertCard(alertCardData: AlertCardData) {
    @OptIn(ExperimentalMaterial3Api::class)
    Card(
        modifier = Modifier.padding(Paddings.xlarge, Paddings.medium),
        colors =
            CardDefaults.cardColors(
                containerColor = BackgroundColor,
            ),
        onClick = alertCardData.onClick,
    ) {
        Column {
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier =
                        Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(White),
                ) {
                    Icon(
                        painter = painterResource(id = alertCardData.icon),
                        contentDescription = stringResource(R.string.alert_icon),
                        tint = Primary,
                        modifier = Modifier.size(36.dp),
                    )
                }
                Column(modifier = Modifier.padding(Paddings.medium)) {
                    Text(
                        text = alertCardData.message,
                        style = Typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = alertCardData.date,
                            style = Typography.labelLarge,
                            color = Gray4,
                        )
                    }
                }
            }
            Spacer(Modifier.size(20.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray4),
            )
        }
    }
}

val alertCardData =
    AlertCardData(
        message = "스왑 요청이 들어왔어요",
        date = "2월 19일 13:22",
        icon = R.drawable.ic_show,
    )

@Preview(showBackground = true)
@Composable
fun AlertCardPreview() {
    AlertCard(
        alertCardData,
    )
}
