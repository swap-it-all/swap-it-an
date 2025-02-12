package com.example.swap_it.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swap_it.R
import com.example.swap_it.ui.theme.Black
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.Shapes
import com.example.swap_it.ui.theme.Typography

@Composable
fun SearchField(
    searchTerm: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = searchTerm,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(R.string.shopping_search_button_content), style = Typography.bodyMedium)
        },
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Gray3,
            unfocusedLabelColor = Gray3,
            focusedContainerColor = Gray6,
            unfocusedContainerColor = Gray6,
            focusedIndicatorColor = Gray6,
            unfocusedIndicatorColor = Gray6,
            cursorColor = Black,
        ),
        trailingIcon = {
            Image(
                painter = painterResource(R.drawable.ic_search_magnifying),
                contentDescription = "검색 버튼",
                colorFilter = ColorFilter.tint(Gray3)
            )
        }

    )
}