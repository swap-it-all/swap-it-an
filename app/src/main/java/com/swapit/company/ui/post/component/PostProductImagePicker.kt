package com.swapit.company.ui.post.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swapit.company.R
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Gray3
import com.swapit.company.ui.theme.Gray4
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Shapes

@Composable
fun PostProductImagePicker(
    count: Int,
    maxCount: Int,
    color: Color,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(86.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = BackgroundColor,
                contentColor = color,
            ),
        shape = Shapes.small,
        border = BorderStroke(1.dp, Gray4),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = "이미지",
            )
            Text(text = "$count/$maxCount")
        }
    }
    Spacer(modifier = Modifier.padding(Paddings.medium))
}

@Preview(showBackground = true)
@Composable
fun PostProductImagePickerPreview() {
    PostProductImagePicker(
        count = 1,
        maxCount = 10,
        color = Gray3,
        onClick = {},
    )
}
