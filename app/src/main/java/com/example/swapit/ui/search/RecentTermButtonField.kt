package com.example.swapit.ui.search

import ShoppingViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swapit.ui.component.SearchTermButton
import com.example.swapit.ui.theme.Paddings

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecentTermButtonField(viewModel: ShoppingViewModel) {
    FlowRow(
        horizontalArrangement = Arrangement.Start,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Paddings.mediumLarge),
    ) {
        viewModel.recentKeyword.value.forEach {
            SearchTermButton(
                text = it,
                modifier = Modifier.padding(Paddings.smallMedium),
            ) {
            }
        }
    }
}
