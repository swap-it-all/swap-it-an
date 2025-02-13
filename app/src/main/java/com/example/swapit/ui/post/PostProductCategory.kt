package com.example.swapit.ui.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swapit.data.datasource.local.model.post.CategoryOption
import com.example.swapit.ui.component.CategoryButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostProductCategory(
    categoryOptions: List<CategoryOption>,
    option: CategoryOption? = null,
    onCategorySelected: (CategoryOption) -> Unit,
) {
    FlowRow(horizontalArrangement = Arrangement.Start) {
        categoryOptions.forEach { category ->
            CategoryButton(category.option, isSelected = category == option) {
                onCategorySelected(category)
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostProductCategoryPreview() {
    PostProductCategory(CategoryOption.entries.toList(), CategoryOption.ELECTRONICS) {}
}
