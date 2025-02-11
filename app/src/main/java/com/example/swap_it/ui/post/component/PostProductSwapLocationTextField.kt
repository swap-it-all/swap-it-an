package com.example.swap_it.ui.post.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.swap_it.R
import com.example.swap_it.domain.model.post.Location
import com.example.swap_it.domain.model.post.Location.Companion.LOCATION_RANGE
import com.example.swap_it.domain.model.post.ValidationResult
import com.example.swap_it.ui.component.DefaultTextField

@Composable
fun PostProductSwapLocationTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
) {
    val validationResult = Location(value).validationResult()
    val supportingText =
        when (validationResult) {
            ValidationResult.INVALID_RANGE ->
                stringResource(
                    R.string.post_product_error_location_length,
                    LOCATION_RANGE.last,
                )

            else -> ""
        }

    DefaultTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = singleLine,
        placeholder = { Text(text = stringResource(R.string.post_product_placeholder_location)) },
        supportingText = { if (supportingText.isNotEmpty()) Text(text = supportingText) },
        isError = supportingText.isNotEmpty(),
    )
}

@Preview(showBackground = true)
@Composable
fun PostProductSwapLocationTextFieldPreview() {
    PostProductSwapLocationTextField(
        value = "",
        onValueChange = {},
    )
}
