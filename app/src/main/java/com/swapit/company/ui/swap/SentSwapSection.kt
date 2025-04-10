package com.swapit.company.ui.swap

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.swapit.company.R
import com.swapit.company.ui.navigation.NavItem
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Typography

@Composable
fun SentSwapSection(
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
                navController.navigate(NavItem.SENT_SWAP)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.swap_request_message),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Icon(
            painter = painterResource(R.drawable.ic_chevron_right),
            contentDescription = stringResource(R.string.swap_go_to_sent),
        )
    }
    LazyRow {
        items(
            viewModel.sentSwap.value.size,
            key = { index -> viewModel.sentSwap.value[index].tradesId },
        ) { index ->
            val sentSwap = viewModel.sentSwap.value[index]
            SentSwapCard(sentSwap = sentSwap, navController = navController)
        }
    }
}
