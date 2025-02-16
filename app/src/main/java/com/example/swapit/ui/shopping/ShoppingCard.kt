package com.example.swapit.ui.shopping

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.swapit.R
import com.example.swapit.data.model.ShoppingCardData
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White
import java.text.DecimalFormat

@Composable
fun ShoppingCard(
    shoppingCardData: ShoppingCardData,
    navController: NavHostController,
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
                model = shoppingCardData.imageUri,
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
                    text = "${shoppingCardData.category} | ${shoppingCardData.region} | ${shoppingCardData.time}",
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
                    text = shoppingCardData.title,
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
                            text = decimal.format(shoppingCardData.price),
                            style = Typography.titleMedium,
                        )
                        Text(
                            text = stringResource(R.string.won),
                            style = Typography.bodyMedium,
                            color = Gray3,
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_show),
                        contentDescription = stringResource(R.string.view_count_icon_description),
                        colorFilter = ColorFilter.tint(Gray4),
                    )
                    Text(
                        text = shoppingCardData.viewCount,
                        style = Typography.labelLarge,
                        color = Gray4,
                    )
                }
            }
        }
    }
}

val productCardData =
    ShoppingCardData(
        imageUri = "https://static.nike.com/a/images",
        category = "가방",
        viewCount = "100",
        region = "강서구",
        time = "1일전",
        price = 1000000,
        title = "나이키 운동화",
        goodsId = 1,
    )

@Preview(showBackground = true)
@Composable
fun ShoppingCardPreview() {
    ShoppingCard(
        productCardData,
        rememberNavController(),
    )
}
