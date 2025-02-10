package com.example.swap_it.ui.post

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.data.datasource.local.model.post.QualityOption
import com.example.swap_it.ui.component.CategoryButton

@Composable
fun PostProductQuality(
    qualityOptions: List<QualityOption>
) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(qualityOptions) { qualityOptions ->
            CategoryButton(qualityOptions.option, isSelected = false) {

            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostProductQualityPreview() {
    PostProductQuality(QualityOption.entries.toList())
}
