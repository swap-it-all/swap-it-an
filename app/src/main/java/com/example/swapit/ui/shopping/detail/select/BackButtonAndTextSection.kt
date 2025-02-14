package com.example.swapit.ui.shopping.detail.select

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.swapit.R
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography

@Composable
fun BackButtonAndTextSection(navController: NavHostController) {
    BackButton(
        Modifier.padding(
            Paddings.xlarge,
            Paddings.xlarge,
            Paddings.none,
            Paddings.xextra,
        ),
        navController,
        color = Black,
    )
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
