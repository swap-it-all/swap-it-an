package com.swapit.company.ui.alert

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.R
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Black
import com.swapit.company.ui.theme.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertAppBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = {
            CancelButton(
                modifier = Modifier.padding(start = Paddings.xlarge),
                navController = navController,
            )
        },
        title = {},
        actions = { Spacer(modifier = Modifier.size(24.dp).padding(end = Paddings.xlarge)) },
        colors =
            TopAppBarColors(
                containerColor = BackgroundColor,
                navigationIconContentColor = BackgroundColor,
                actionIconContentColor = BackgroundColor,
                scrolledContainerColor = BackgroundColor,
                titleContentColor = BackgroundColor,
            ),
    )
}

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    color: Color = Black,
) {
    IconButton(
        onClick = {
            navController.navigateUp()
        },
        modifier = modifier.size(24.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "뒤로 가기",
            tint = color,
        )
    }
}
