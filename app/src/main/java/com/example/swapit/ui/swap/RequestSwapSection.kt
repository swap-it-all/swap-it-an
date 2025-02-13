package com.example.swapit.ui.swap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swapit.R
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Typography

@Composable
fun RequestSwapSection(
    modifier: Modifier,
    navController: NavHostController,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(Paddings.xlarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.swap_request_message),
            style = Typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        IconButton(
            onClick = {
                navController.navigateUp()
            },
            modifier = modifier.size(24.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = "요청한 스왑으로 가기",
            )
        }
    }
    LazyRow {
        items(10) {
            SwapCard(swapCardData = swapCardData)
        }
    }
}
