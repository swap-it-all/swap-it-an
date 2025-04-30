package com.swapit.oopswap.ui.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.swapit.oopswap.ui.theme.BackgroundColor
import com.swapit.oopswap.ui.theme.Gray3
import com.swapit.oopswap.ui.theme.Gray4
import com.swapit.oopswap.ui.theme.Shapes
import com.swapit.oopswap.ui.theme.Typography

@Composable
fun DefaultTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    minLines: Int = 1,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier,
        shape = Shapes.medium,
        textStyle = Typography.bodyMedium,
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = BackgroundColor,
                unfocusedContainerColor = BackgroundColor,
                errorContainerColor = BackgroundColor,
                focusedTextColor = Gray3,
                unfocusedTextColor = Gray3,
                focusedIndicatorColor = Gray4,
                unfocusedIndicatorColor = Gray4,
            ),
        keyboardOptions =
            KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
        keyboardActions =
            KeyboardActions(
                onDone = { focusManager.clearFocus() },
            ),
        singleLine = singleLine,
        placeholder = placeholder,
        supportingText = supportingText,
        isError = isError,
        minLines = minLines,
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
