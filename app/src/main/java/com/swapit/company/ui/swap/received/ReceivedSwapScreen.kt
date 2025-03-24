package com.swapit.company.ui.swap.received

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.swapit.company.domain.model.swap.ReceivedSwap
import com.swapit.company.ui.component.BackButton
import com.swapit.company.ui.navigation.NavItem
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Typography

@Composable
fun ReceivedSwapScreen(
    navController: NavHostController,
    receivedSwaps: List<ReceivedSwap>,
) {
    Scaffold(topBar = { ReceivedSwapTopBar(navController = navController) }) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(contentPadding),
        ) {
            items(
                receivedSwaps.size,
                key = { index -> receivedSwaps[index].goodsId },
            ) { index ->
                val receivedSwap = receivedSwaps[index]
                ReceivedSwapScreenCard(cardData = receivedSwap, onClick = {
                    navController.navigate(NavItem.ReceivedDetailSwap.screenRoute + "/${receivedSwap.goodsId}")
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceivedSwapTopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    "받은 스왑 요청",
                    style = Typography.bodyMedium,
                )
            }
        },
        navigationIcon = {
            BackButton(
                modifier = Modifier.padding(Paddings.xlarge),
                navController = navController,
            )
        },
        actions = {
            Spacer(Modifier.padding(Paddings.extra))
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ReceivedSwapTopBarPreview() {
    ReceivedSwapTopBar(navController = NavHostController(LocalContext.current))
}
