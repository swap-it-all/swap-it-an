package com.example.swap_it.ui.post.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.swap_it.R
import com.example.swap_it.domain.model.post.Name
import com.example.swap_it.domain.model.post.Name.Companion.PRODUCT_NAME_RANGE
import com.example.swap_it.domain.model.post.ValidationResult
import com.example.swap_it.ui.component.DefaultTextField

@Composable
internal fun PostProductNameTextField(
    text: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val validationResult = Name(text).validationResult()
    val supportingText =
        when (validationResult) {
            ValidationResult.INVALID_LENGTH ->
                stringResource(
                    R.string.post_product_error_name_length,
                    PRODUCT_NAME_RANGE.first,
                    PRODUCT_NAME_RANGE.last,
                )

            else -> ""
        }

    DefaultTextField(
        value = text,
        onValueChange = onNameChange,
        modifier = modifier,
        singleLine = true,
        placeholder = { Text(text = stringResource(R.string.post_product_placeholder_name)) },
        supportingText = { if (supportingText.isNotEmpty()) Text(text = supportingText) },
        isError = supportingText.isNotEmpty(),
    )
}

@Preview(showBackground = true)
@Composable
internal fun PostProductNameTextFieldPreview() {
    PostProductNameTextField(
        text = "",
        onNameChange = {},
    )
}
