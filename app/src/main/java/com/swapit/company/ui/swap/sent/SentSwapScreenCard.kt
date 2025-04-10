package com.swapit.company.ui.swap.sent

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swapit.company.R
import com.swapit.company.data.datasource.local.model.post.CategoryOption
import com.swapit.company.domain.model.swap.SentSwap
import com.swapit.company.ui.theme.Gray3
import com.swapit.company.ui.theme.Gray4
import com.swapit.company.ui.theme.Gray6
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Primary
import com.swapit.company.ui.theme.Typography
import com.swapit.company.ui.theme.White

@Composable
fun SentSwapScreenCard(
    cardData: SentSwap,
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

            Box(modifier = Modifier.size(86.dp)) {
                AsyncImage(
                    model = cardData.myGoodsPhotoUrl,
                    contentDescription = stringResource(R.string.president_image_description),
                    modifier =
                        Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .align(Alignment.TopStart),
                    placeholder = ColorPainter(Primary),
                    fallback = rememberVectorPainter(Icons.Default.Call),
                    error = rememberVectorPainter(Icons.Default.Settings),
                    contentScale = ContentScale.Crop,
                )
                AsyncImage(
                    model = cardData.targetGoodsPhotoUrl,
                    contentDescription = stringResource(R.string.president_image_description),
                    modifier =
                        Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .align(Alignment.BottomEnd),
                    placeholder = ColorPainter(Primary),
                    fallback = rememberVectorPainter(Icons.Default.Call),
                    error = rememberVectorPainter(Icons.Default.Settings),
                    contentScale = ContentScale.Crop,
                )
            }

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
                    text = "${CategoryOption.entries.find { it.name == cardData.category }?.option} | ${cardData.placeName}",
                    style = Typography.labelLarge,
                    color = Gray4,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (cardData.isInProgress) {
                        Box(
                            modifier =
                                Modifier
                                    .size(53.dp, 24.dp).clip(RoundedCornerShape(12.dp))
                                    .background(
                                        Gray6,
                                    ),
                        ) {
                            Text(
                                modifier =
                                    Modifier.align(Alignment.Center),
                                text = "거래중",
                                style = Typography.titleSmall,
                                color = Primary,
                            )
                        }
                        Spacer(modifier = Modifier.size(Paddings.small))
                    }
                    Text(
                        modifier =
                            Modifier.padding(
                                Paddings.none,
                                Paddings.none,
                                Paddings.none,
                                Paddings.small,
                            ),
                        text = cardData.title,
                        style = Typography.bodyMedium,
                        maxLines = 1,
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
fun SentSwapScreenCardPreview() {
    SentSwapScreenCard(
        cardData =
            SentSwap(
                tradesId = 1,
                goodsId = 1,
                title = "dd",
                price = 10000,
                category = "FOOD",
                placeName = "dd",
                myGoodsPhotoUrl = "dd",
                targetGoodsPhotoUrl = "dd",
                targetGoodsViewCount = 1,
                createdAt = "dd",
                isInProgress = false,
            ),
        onClick = {},
    )
}
