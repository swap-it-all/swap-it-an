package com.example.swapit.ui.swap.sent

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
import androidx.navigation.NavHostController
import com.example.swapit.domain.model.swap.SentSwap
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.navigation.NavItem
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Typography

@Composable
fun SentSwapScreen(
    navController: NavHostController,
    sentSwaps: List<SentSwap>,
) {
    Scaffold(topBar = { SentSwapTopBar(navController = navController) }) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(contentPadding),
        ) {
            items(
                sentSwaps.size,
                key = { index -> sentSwaps[index].tradesId },
            ) { index ->
                val sentSwap = sentSwaps[index]
                SentSwapScreenCard(cardData = sentSwap, onClick = {
                    navController.navigate(NavItem.ShoppingDetail.screenRoute + "/${sentSwap.goodsId}")
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentSwapTopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    "보낸 스왑 요청",
                    style = Typography.bodyMedium,
                )
            }
        },
        navigationIcon = { BackButton(modifier = Modifier.padding(Paddings.xlarge), navController = navController) },
        actions = {
            Spacer(Modifier.padding(Paddings.extra))
        },
    )
}
