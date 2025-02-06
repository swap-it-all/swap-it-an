package com.example.swap_it.ui.component

import androidx.compose.foundation.border
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Shapes
import com.example.swap_it.ui.theme.Typography
import com.example.swap_it.ui.theme.White

@Composable
fun DefaultTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier.border(1.dp, color = Gray4, shape = Shapes.medium),
        textStyle = Typography.bodyMedium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = White,  // todo color change
            unfocusedContainerColor = White,
            focusedTextColor = Gray3,
            unfocusedTextColor = Gray3,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultTextFieldPreview() {
    DefaultTextField(
        value = "",
        onValueChange = {},
    )
}
