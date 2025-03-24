package com.swapit.company.ui.shopping

import ShoppingViewModel
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.swapit.company.R
import com.swapit.company.data.datasource.local.model.bottomsheet.SortOption
import com.swapit.company.data.datasource.local.model.post.CategoryOption
import com.swapit.company.ui.component.CategoryButton
import com.swapit.company.ui.theme.Gray5
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Primary
import com.swapit.company.ui.theme.Typography

@Composable
fun ShoppingSortBottomSheetContent(viewModel: ShoppingViewModel) {
    Column(modifier = Modifier.padding(Paddings.large)) {
        Text(text = stringResource(R.string.shopping_arrange), style = Typography.titleLarge)
        SortButtons(viewModel = viewModel)
        Text(text = stringResource(R.string.shopping_category_title), style = Typography.titleLarge)
        CategoryButtons(viewModel = viewModel)
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
fun SortButtons(viewModel: ShoppingViewModel) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        SortOption.entries.forEach {
            SortButton(
                text = it.option,
                isSelected = viewModel.selectedOption.value == it,
                onClick = {
                    viewModel.selectOption(it)
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
fun CategoryButtons(viewModel: ShoppingViewModel) {
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
                isSelected = viewModel.selectedCategory.value.contains(it),
                modifier = Modifier.padding(Paddings.small),
                onClick = {
                    viewModel.selectCategory(it)
                },
            )
        }
    }
}
