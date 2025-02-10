package com.example.swap_it.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.swap_it.R
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray6
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
        shape = TextFieldDefaults.TextFieldShape,
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color.Black,
            focusedBorderColor = Gray6,
            unfocusedBorderColor = Gray6,
            cursorColor = Gray6,
            backgroundColor = Gray6
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