package com.example.swapit.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.R
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Gray2
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Gray6
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.PrimaryDark
import com.example.swapit.ui.theme.Shapes
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    contentStyle: TextStyle = Typography.titleMedium,
    contentPadding: PaddingValues =
        PaddingValues(
            horizontal = Paddings.xextra,
            vertical = Paddings.xlarge,
        ),
    interactionSource: InteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        contentPadding = contentPadding,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = if (isPressed) PrimaryDark else Primary,
                contentColor = White,
                disabledContainerColor = Gray6,
            ),
        shape = Shapes.small,
    ) {
        Text(
            text = text,
            style = contentStyle,
        )
    }
}

@Composable
fun ModalButton(
    text: String,
    modifier: Modifier = Modifier,
    contentStyle: TextStyle = Typography.titleMedium,
    contentPadding: PaddingValues =
        PaddingValues(
            horizontal = Paddings.extra,
            vertical = Paddings.large,
        ),
    containerColor: Color = Primary,
    contentColor: Color = White,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedContainerColor by animateColorAsState(
        targetValue =
            when (containerColor) {
                Primary -> if (isPressed) PrimaryDark else containerColor
                else -> if (isPressed) Gray4 else containerColor
            },
    )
    val animatedContentColor by animateColorAsState(
        targetValue =
            when (animatedContainerColor) {
                Primary, PrimaryDark -> White
                else -> Gray3
            },
    )

    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = contentPadding,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = animatedContainerColor,
                contentColor = animatedContentColor,
            ),
        interactionSource = interactionSource,
        shape = Shapes.small,
    ) {
        Text(
            text = text,
            style = contentStyle,
        )
    }
}

@Composable
fun CategoryButton(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    contentStyle: TextStyle = Typography.bodySmall,
    contentPadding: PaddingValues =
        PaddingValues(
            horizontal = Paddings.xlarge,
            vertical = Paddings.medium,
        ),
    containerColor: Color = White,
    contentColor: Color = Gray4,
    onClick: () -> Unit,
) {
    val animatedBorderColor by animateColorAsState(
        targetValue = if (isSelected) Primary else contentColor,
    )
    val animatedContentColor by animateColorAsState(
        targetValue = if (isSelected) Primary else contentColor,
    )

    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = contentPadding,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = animatedContentColor,
            ),
        shape = Shapes.medium,
        border = BorderStroke(1.dp, animatedBorderColor),
    ) {
        Text(
            text = text,
            style = contentStyle,
        )
    }
}

@Composable
fun SearchTermButton(
    text: String,
    modifier: Modifier = Modifier,
    contentStyle: TextStyle = Typography.bodySmall,
    contentPadding: PaddingValues =
        PaddingValues(
            horizontal = Paddings.xlarge,
            vertical = Paddings.smallMedium,
        ),
    containerColor: Color = Gray6,
    contentColor: Color = Gray2,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = contentPadding,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
        shape = Shapes.medium,
    ) {
        Text(
            text = text,
            style = contentStyle,
        )
    }
}

@Composable
fun BackButton(
    modifier: Modifier,
    navController: NavHostController,
    color: Color,
) {
    IconButton(
        onClick = {
            navController.navigateUp()
        },
        modifier = modifier.size(24.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_chevron_left),
            contentDescription = "뒤로 가기",
            tint = color,
        )
    }
}

@Composable
fun MenuButton(
    modifier: Modifier,
    navController: NavHostController,
    color: Color,
) {
    IconButton(
        onClick = {
            navController.navigateUp()
        },
        modifier = modifier.size(24.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_menu),
            contentDescription = "메뉴",
            tint = color,
        )
    }
}

class EnabledPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values =
        sequenceOf(
            true,
            false,
        )
}

@Preview(showBackground = true)
@Composable
fun DefaultButtonPreview(
    @PreviewParameter(EnabledPreviewParameterProvider::class) enabled: Boolean,
) {
    DefaultButton(
        text = if (enabled) "활성화된 버튼" else "비활성화된 버튼",
        onClick = {},
        contentStyle = Typography.titleMedium,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
    )
}

class ModalButtonPreviewParameterProvider : PreviewParameterProvider<Color> {
    override val values =
        sequenceOf(
            Primary,
            Gray5,
        )
}

@Preview(showBackground = true)
@Composable
fun ModalButtonPreview(
    @PreviewParameter(ModalButtonPreviewParameterProvider::class) containerColor: Color,
) {
    ModalButton(
        text = "모달 버튼",
        onClick = {},
        containerColor = containerColor,
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryButtonPreview() {
    CategoryButton(
        text = "카테고리 버튼",
        onClick = {},
        isSelected = false,
    )
}

@Preview(showBackground = true)
@Composable
fun SearchButtonPreview() {
    SearchTermButton(
        text = "검색 버튼",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun BackButtonPreview() {
    BackButton(Modifier, rememberNavController(), Black)
}

@Preview(showBackground = true)
@Composable
fun MenuButtonPreview() {
    MenuButton(Modifier, rememberNavController(), Black)
}
