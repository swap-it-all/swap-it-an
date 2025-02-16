package com.example.swapit.ui.shopping

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.data.datasource.local.model.bottomsheet.SortOption
import com.example.swapit.data.datasource.local.model.post.CategoryOption
import com.example.swapit.ui.component.CategoryButton
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography

@Composable
fun ModalBottomSheetContent() {
    var option = SortOption.POPULAR
    Column(modifier = Modifier.padding(Paddings.large)) {
        Text(text = stringResource(R.string.shopping_arrange), style = Typography.titleLarge)
        SortButtons(onSortOptionSelected = { option = it })
        Text(text = stringResource(R.string.shopping_category_title), style = Typography.titleLarge)
        CategoryButtons()
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
fun SortButtons(
    initialSortOption: SortOption = SortOption.POPULAR,
    onSortOptionSelected: (SortOption) -> Unit,
) {
    var selectedOption by remember { mutableStateOf(initialSortOption) }
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        SortOption.entries.forEach {
            SortButton(
                text = it.option,
                isSelected = selectedOption == it,
                onClick = {
                    selectedOption = it
                    onSortOptionSelected(it)
                },
            )
        }
    }
}

@Composable
fun SortButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val textColor = if (isSelected) Primary else Gray5
    TextButton(onClick = onClick) {
        Row {
            if (isSelected) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = stringResource(R.string.shopping_select_icon),
                    tint = Primary,
                    modifier = Modifier.size(16.dp),
                )
            }
            Text(text = text, color = textColor, style = Typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryButtons() {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Paddings.medium),
    ) {
        CategoryOption.entries.forEach {
            CategoryButton(
                text = it.option,
                isSelected = true,
                modifier = Modifier.padding(Paddings.small),
            ) {
            }
        }
    }
}
