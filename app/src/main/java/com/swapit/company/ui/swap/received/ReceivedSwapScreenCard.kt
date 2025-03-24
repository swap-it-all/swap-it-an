package com.swapit.company.ui.swap.received

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swapit.company.R
import com.swapit.company.domain.model.swap.ReceivedSwap
import com.swapit.company.ui.theme.Black
import com.swapit.company.ui.theme.Gray3
import com.swapit.company.ui.theme.Gray4
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Primary
import com.swapit.company.ui.theme.Typography
import com.swapit.company.ui.theme.White
import java.text.DecimalFormat

@Composable
fun ReceivedSwapScreenCard(
    cardData: ReceivedSwap,
    onClick: () -> Unit = {},
) {
    val decimal = DecimalFormat(stringResource(R.string.decimal_format))
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Paddings.xlarge, Paddings.smallMedium),
        colors =
            CardDefaults.cardColors(
                containerColor = White,
            ),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.size(Paddings.large))
            AsyncImage(
                model = cardData.photoUrl,
                contentDescription = stringResource(R.string.president_image_description),
                modifier =
                    Modifier
                        .size(86.dp)
                        .clip(RoundedCornerShape(12.dp)),
                placeholder = ColorPainter(Primary),
                fallback = rememberVectorPainter(Icons.Default.Call),
                error = rememberVectorPainter(Icons.Default.Settings),
                contentScale = ContentScale.Crop,
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
                    text = "${cardData.requestCount} 개의 스왑 요청",
                    style = Typography.labelLarge,
                    color = Gray4,
                )
                Row {
                    if (cardData.inProgressCount.toInt() != 0) {
                        Text(
                            modifier =
                                Modifier.padding(
                                    Paddings.small,
                                    Paddings.none,
                                    Paddings.small,
                                    Paddings.small,
                                ),
                            text = "거래중",
                            style = Typography.bodyMedium,
                            color = Primary,
                        )
                    }
                    Text(
                        modifier =
                            Modifier.padding(
                                Paddings.none,
                                Paddings.none,
                                Paddings.none,
                                Paddings.small,
                            ).weight(1f),
                        text = cardData.title,
                        style = Typography.bodyMedium,
                        maxLines = 1,
                    )
                    Icon(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        painter = painterResource(R.drawable.ic_chevron_right),
                        contentDescription = "상세 정보 아이콘",
                        tint = Black,
                    )
                }
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
                            text = decimal.format(cardData.price),
                            style = Typography.bodyMedium,
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
    }
}

@Preview(showBackground = true)
@Composable
fun ReceivedSwapScreenCardPreview() {
    ReceivedSwapScreenCard(
        cardData =
            ReceivedSwap(
                goodsId = 1,
                title = "dd",
                price = 10000,
                category = "FOOD",
                placeName = "dd",
                viewCount = 1,
                photoUrl = "dd",
                requestCount = 1,
                inProgressCount = 1,
                createdAt = "dd",
            ),
        onClick = {},
    )
}
