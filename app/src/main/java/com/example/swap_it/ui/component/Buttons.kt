package com.example.swap_it.ui.component

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import com.example.swap_it.ui.theme.Shapes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.swap_it.ui.theme.Gray5
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.PrimaryDark
import com.example.swap_it.ui.theme.Typography
import com.example.swap_it.ui.theme.White

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    contentStyle: TextStyle = Typography.titleMedium,
    contentPadding: PaddingValues = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
    interactionSource: InteractionSource,
    onClick: () -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        contentPadding = contentPadding,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) PrimaryDark else Primary,
            contentColor = White,
            disabledContainerColor = Gray5,
        ),
        shape = Shapes.large,
    ) {
        Text(
            text = text,
            style = contentStyle,
        )
    }
}

/*
class EnabledPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false,
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultButtonPreview(
    @PreviewParameter(EnabledPreviewParameterProvider::class) enabled: Boolean
) {
    DefaultButton(
        text = if (enabled) "활성화된 버튼" else "비활성화된 버튼",
        onClick = {},
        contentStyle = Typography.titleMedium,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }
    )
}
*/
