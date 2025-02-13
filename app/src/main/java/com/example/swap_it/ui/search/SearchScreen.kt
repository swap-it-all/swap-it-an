package com.example.swap_it.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.ui.component.BackButton
import com.example.swap_it.ui.theme.BackgroundColor
import com.example.swap_it.ui.theme.Black
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.Typography


@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel
) {
    var searchTerm by remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Spacer(Modifier.size(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    Paddings.none, Paddings.none, Paddings.xlarge, Paddings.xlarge
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(Modifier.padding(Paddings.medium), navController, color = Black)
            SearchField(
                searchTerm,
                Modifier,
                onValueChange = {
                    searchTerm = it
                }
            )
        }
        Text(
            stringResource(R.string.search_current_term),
            style = Typography.titleLarge,
            modifier = Modifier.padding(
                Paddings.xlarge,
                Paddings.large,
                Paddings.none,
                Paddings.smallMedium
            )
        )
        RecentTermButtonField(viewModel)
        Text(
            stringResource(R.string.search_popular_term),
            style = Typography.titleLarge,
            modifier = Modifier.padding(
                Paddings.xlarge,
                Paddings.xextra,
                Paddings.none,
                Paddings.smallMedium
            )
        )
        PopularTermButtonField(viewModel)
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SwapitTheme {
        SearchScreen(rememberNavController(),SearchViewModel())
    }
}


