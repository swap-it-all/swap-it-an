package com.example.swapit.ui.search

import SearchField
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
fun SearchAppBar(
    navController: NavHostController,
    searchTerm: String,
    onValueChange: (String) -> Unit,
) {
    TopAppBar(
        navigationIcon = { BackButton(modifier = Modifier.padding(Paddings.large), navController = navController) },
        title = {
            SearchField(
                searchTerm,
                Modifier,
                onValueChange = onValueChange
            )
        },
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