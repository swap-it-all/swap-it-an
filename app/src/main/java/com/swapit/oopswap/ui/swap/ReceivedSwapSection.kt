package com.swapit.oopswap.ui.swap

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.oopswap.R
import com.swapit.oopswap.ui.navigation.NavItem
import com.swapit.oopswap.ui.theme.Paddings
import com.swapit.oopswap.ui.theme.Typography

@Composable
fun ReceivedSwapSection(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SwapViewModel,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.xlarge)
            .height(48.dp)
            .clickable {
                navController.navigate(NavItem.RECEIVED_SWAP)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.swap_requested_message),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Icon(
            painter = painterResource(R.drawable.ic_chevron_right),
            contentDescription = stringResource(R.string.swap_go_to_received),
        )
    }
    LazyRow {
        items(
            viewModel.receivedSwap.value.size,
            key = { index -> viewModel.receivedSwap.value[index].goodsId },
        ) { index ->
            val receivedSwap = viewModel.receivedSwap.value[index]
            ReceivedSwapCard(receivedSwap = receivedSwap, navController = navController)
        }
    }
}
