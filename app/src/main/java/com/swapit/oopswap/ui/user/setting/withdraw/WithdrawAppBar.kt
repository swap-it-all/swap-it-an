package com.swapit.oopswap.ui.user.setting.withdraw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.oopswap.ui.component.BackButton
import com.swapit.oopswap.ui.theme.BackgroundColor
import com.swapit.oopswap.ui.theme.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawAppBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = { BackButton(modifier = Modifier.padding(start = Paddings.xlarge), navController = navController) },
        title = { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) { Text("탈퇴하기") } },
        colors =
            TopAppBarColors(
                containerColor = BackgroundColor,
                navigationIconContentColor = BackgroundColor,
                actionIconContentColor = BackgroundColor,
                scrolledContainerColor = BackgroundColor,
                titleContentColor = BackgroundColor,
            ),
        actions = { Spacer(modifier = Modifier.size(24.dp).padding(end = Paddings.xlarge)) },
    )
}
