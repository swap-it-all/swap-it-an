package com.example.swap_it.ui.post.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.swap_it.R
import com.example.swap_it.domain.model.post.Description
import com.example.swap_it.domain.model.post.Description.Companion.DESCRIPTION_RANGE
import com.example.swap_it.domain.model.post.ValidationResult
import com.example.swap_it.ui.component.DefaultTextField

@Composable
fun PostProductDescriptionTextField(
    text: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
) {
    val validationResult = Description(text).validationResult()
    val supportingText = when (validationResult) {
        ValidationResult.INVALID_RANGE -> stringResource(
            R.string.post_product_error_description_length,
            DESCRIPTION_RANGE.last,
        )

        else -> ""
    }

    DefaultTextField(
        value = text,
        onValueChange = onDescriptionChange,
        modifier = modifier,
        singleLine = singleLine,
        placeholder = { Text(text = stringResource(R.string.post_product_placeholder_description)) },
        supportingText = { if (supportingText.isNotEmpty()) Text(text = supportingText) },
        isError = supportingText.isNotEmpty()
    )
}

@Preview(showBackground = true)
@Composable
fun PostProductDescriptionTextFieldPreview() {
    PostProductDescriptionTextField(
        text = "",
        onDescriptionChange = {},
    )
}
