package com.example.swap_it.ui.post.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.swap_it.R
import com.example.swap_it.domain.model.post.Price
import com.example.swap_it.domain.model.post.Price.Companion.PRODUCT_RANGE
import com.example.swap_it.domain.model.post.ValidationResult
import com.example.swap_it.ui.component.DefaultTextField

@Composable
fun PostProductPriceTextField(
    text: String,
    onPriceChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
) {
    val validationResult = Price(text).validationResult()
    val supportingText = when (validationResult) {
        ValidationResult.INVALID_FORMAT -> stringResource(R.string.post_product_error_price_format)
        ValidationResult.INVALID_RANGE -> stringResource(
            R.string.post_product_error_price_range,
            PRODUCT_RANGE.last,
        )

        else -> ""
    }

    DefaultTextField(
        value = text,
        onValueChange = onPriceChange,
        modifier = modifier,
        singleLine = singleLine,
        placeholder = { Text(text = stringResource(R.string.post_product_placeholder_price)) },
        supportingText = { if (supportingText.isNotEmpty()) Text(text = supportingText) },
        isError = supportingText.isNotEmpty()
    )
}

@Preview(showBackground = true)
@Composable
fun PostProductPriceTextFieldPreview() {
    PostProductPriceTextField(
        text = "",
        onPriceChange = {},
    )
}
