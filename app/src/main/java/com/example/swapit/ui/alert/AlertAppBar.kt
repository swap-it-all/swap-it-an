package com.example.swapit.ui.alert

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertAppBar(
    navController: NavHostController,
) {
    TopAppBar(
        navigationIcon = { BackButton(modifier = Modifier.padding(start = Paddings.xlarge), navController = navController) },
        title = {},
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