package com.example.swap_it.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swap_it.data.datasource.local.model.search.PopularSearch
import com.example.swap_it.ui.component.SearchTermButton
import com.example.swap_it.ui.theme.Paddings

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PopularTermButtonField(viewModel: SearchViewModel) {
    FlowRow(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Paddings.mediumLarge),
    ) {
        viewModel.allPopularSearchTerm.forEach {
            SearchTermButton(
                text = it.categoryName,
                modifier = Modifier.padding(Paddings.smallMedium)
            ) {

            }
        }
    }
}