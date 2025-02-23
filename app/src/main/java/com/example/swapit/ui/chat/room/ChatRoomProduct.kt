package com.example.swapit.ui.chat.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.swapit.R
import com.example.swapit.data.model.ChatRoomData
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White
import java.text.DecimalFormat

val _chatRoomData =
    ChatRoomData(
        imageUri = "https://static.nike.com/a/images",
        category = "가방",
        price = 1000000,
        title = "나이키 운동화",
        goodsId = 1,
    )

@Composable
fun ChatRoomProduct(chatRoomData: ChatRoomData = _chatRoomData) {
    val decimal = DecimalFormat(stringResource(R.string.decimal_format))
    Column {
        HorizontalDivider(thickness = 1.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier.background(
                    White,
                ),
        ) {
            Spacer(modifier = Modifier.size(Paddings.large))
            AsyncImage(
                model = chatRoomData.imageUri,
                contentDescription = stringResource(R.string.president_image_description),
                modifier =
                    Modifier
                        .size(86.dp)
                        .clip(RoundedCornerShape(12.dp)),
                placeholder = ColorPainter(Primary),
                fallback = rememberVectorPainter(Icons.Default.Call),
                error = rememberVectorPainter(Icons.Default.Settings),
            )
            Column(
                modifier =
                    Modifier.padding(
                        Paddings.large,
                        Paddings.none,
                        Paddings.none,
                        Paddings.none,
                    ),
            ) {
                Text(
                    modifier =
                        Modifier.padding(
                            Paddings.none,
                            Paddings.largeExtra,
                            Paddings.none,
                            Paddings.xsmall,
                        ),
                    text = chatRoomData.category,
                    style = Typography.labelLarge,
                    color = Gray4,
                )
                Text(
                    modifier =
                        Modifier.padding(
                            Paddings.none,
                            Paddings.none,
                            Paddings.none,
                            Paddings.small,
                        ),
                    text = chatRoomData.title,
                    style = Typography.titleMedium,
                    maxLines = 1,
                )
                Row(
                    modifier =
                        Modifier.padding(
                            Paddings.none,
                            Paddings.none,
                            Paddings.extra,
                            Paddings.largeExtra,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.prediction),
                            style = Typography.bodyMedium,
                            color = Gray3,
                        )
                        Text(
                            text = decimal.format(chatRoomData.price),
                            style = Typography.titleMedium,
                        )
                        Text(
                            text = stringResource(R.string.won),
                            style = Typography.bodyMedium,
                            color = Gray3,
                        )
                    }
                }
            }
        }
        HorizontalDivider(thickness = 1.dp)
    }
}
