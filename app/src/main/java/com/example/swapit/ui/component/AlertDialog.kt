package com.example.swapit.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Shapes
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

@Composable
fun AlertDialog(
    title: String,
    imgUri: String = "",
    description: String = "",
    cancelText: String = "취소",
    confirmText: String = "등록하기",
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onClickCancel() },
        properties =
            DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .width(348.dp)
                        .wrapContentHeight()
                        .background(
                            color = White,
                        ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                if (imgUri.isNotEmpty()) {
                    AsyncImage(
                        model = imgUri,
                        contentDescription = "교환할 내 상품 사진",
                        modifier =
                            Modifier
                                .size(150.dp)
                                .clip(Shapes.small),
                    )
                    Spacer(modifier = Modifier.height(Paddings.large))
                }

                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = Typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(48.dp))

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(horizontal = 16.dp),
                ) {
                    ModalButton(
                        text = cancelText,
                        containerColor = Gray5,
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(44.dp),
                        onClick = { onClickCancel() },
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    ModalButton(
                        text = confirmText,
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(44.dp),
                        onClick = { onClickConfirm() },
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertDialogPreview() {
    AlertDialog(
        title = "Title",
        description = "description",
        onClickCancel = {},
        onClickConfirm = {},
    )
}
