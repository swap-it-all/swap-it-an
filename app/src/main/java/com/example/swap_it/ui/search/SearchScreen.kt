package com.example.swap_it.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.data.datasource.local.model.search.CurrentSearch
import com.example.swap_it.data.datasource.local.model.search.PopularSearch
import com.example.swap_it.ui.component.BackButton
import com.example.swap_it.ui.component.SearchTermButton
import com.example.swap_it.ui.theme.BackgroundColor
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.Typography


@Composable
fun SearchScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var searchTerm by remember { mutableStateOf("") }
    Column(
        modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Spacer(Modifier.size(32.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    Paddings.none, Paddings.none, Paddings.xlarge, Paddings.xlarge
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(modifier.padding(Paddings.medium), navController)
            SearchField(
                searchTerm,
                modifier,
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
        CurrentTermButtonField()
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
        PopularTermButtonField()
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SwapitTheme {
        SearchScreen(Modifier, rememberNavController())
    }
}


