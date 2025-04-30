package com.swapit.oopswap.ui.swap.received.detail

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
import com.swapit.oopswap.domain.model.swap.ReceivedSwapProduct
import com.swapit.oopswap.ui.component.BackButton
import com.swapit.oopswap.ui.navigation.NavItem
import com.swapit.oopswap.ui.theme.Paddings
import com.swapit.oopswap.ui.theme.Typography

@Composable
fun ReceivedSwapDetailScreen(
    navController: NavHostController,
    myProductName: String,
    receivedSwapProducts: List<ReceivedSwapProduct>,
) {
    Scaffold(topBar = { ReceivedSwapDetailTopBar(myProductName, navController) }) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(contentPadding),
        ) {
            items(
                receivedSwapProducts.size,
                key = { index -> receivedSwapProducts[index].goodsId },
            ) { index ->
                val receivedSwapProduct = receivedSwapProducts[index]
                ReceivedSwapDetailScreenCard(cardData = receivedSwapProduct, onClick = {
                    navController.navigate(NavItem.ShoppingDetail.screenRoute + "/${receivedSwapProduct.goodsId}")
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceivedSwapDetailTopBar(
    myProductName: String,
    navController: NavHostController,
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    myProductName,
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
