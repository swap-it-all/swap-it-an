package com.swapit.oopswap.ui.shopping.detail.select

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.swapit.oopswap.R
import com.swapit.oopswap.ui.theme.Paddings
import com.swapit.oopswap.ui.theme.Primary
import com.swapit.oopswap.ui.theme.Typography

@Composable
fun TextSection(navController: NavHostController) {
    Text(
        stringResource(R.string.product_select_request_message),
        style = Typography.titleLarge,
        modifier = Modifier.padding(Paddings.xlarge, Paddings.none),
    )
    Text(
        stringResource(R.string.product_select_request_tip_message),
        style = Typography.labelLarge,
        color = Primary,
        modifier =
            Modifier.padding(
                Paddings.xlarge,
                Paddings.small,
                Paddings.none,
                Paddings.xlarge,
            ),
    )
}
