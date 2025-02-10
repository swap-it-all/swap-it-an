package com.example.swap_it.ui.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.data.datasource.local.model.post.CategoryOption
import com.example.swap_it.ui.component.CategoryButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostProductCategory(
    categoryOptions: List<CategoryOption>
) {
    FlowRow(horizontalArrangement = Arrangement.Start) {
        categoryOptions.forEach { category ->
            CategoryButton(category.option, isSelected = false) {

            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostProductCategoryPreview() {
    PostProductCategory(CategoryOption.entries.toList())
}
