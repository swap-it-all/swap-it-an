package com.example.swapit.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray6
import com.example.swapit.ui.theme.Typography

@Composable
fun SearchField(
    searchTerm: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        value = searchTerm,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(R.string.shopping_search_button_content), style = Typography.bodyMedium)
        },
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.fillMaxWidth(),
        colors =
            TextFieldDefaults.colors(
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
                colorFilter = ColorFilter.tint(Gray3),
            )
        },
    )
}
