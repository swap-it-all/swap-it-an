package com.example.swapit.ui.swap

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.swapit.R
import com.example.swapit.data.model.SwapCardData
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwapCard(swapCardData: SwapCardData) {
    val decimal = DecimalFormat(stringResource(R.string.decimal_format))
    Card(
        modifier =
            Modifier
                .width(162.dp)
                .padding(Paddings.smallMedium),
        colors =
            CardDefaults.cardColors(
                containerColor = White,
            ),
        shape = RoundedCornerShape(20.dp),
        onClick = swapCardData.onClick,
    ) {
        Column {
            AsyncImage(
                model = swapCardData.imageUri,
                contentDescription = stringResource(R.string.president_image_description),
                modifier =
                    Modifier
                        .size(150.dp),
                placeholder = ColorPainter(Primary),
                fallback = rememberVectorPainter(Icons.Default.Call),
                error = rememberVectorPainter(Icons.Default.Settings),
            )
            Column(modifier = Modifier.padding(Paddings.large)) {
                Text(
                    text = "${swapCardData.category} | ${swapCardData.region}",
                    style = Typography.labelLarge,
                    color = Gray4,
                )
                Text(
                    text = swapCardData.title,
                    style = Typography.titleMedium,
                    maxLines = 1,
                    modifier =
                        Modifier.padding(
                            Paddings.none,
                            Paddings.none,
                            Paddings.none,
                            Paddings.small,
                        ),
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier.padding(
                            Paddings.none,
                            Paddings.none,
                            Paddings.none,
                            Paddings.large,
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.prediction),
                        style = Typography.titleMedium,
                        color = Gray3,
                    )
                    Text(
                        text = decimal.format(swapCardData.price),
                        style = Typography.titleMedium,
                    )
                    Text(
                        text = stringResource(R.string.won),
                        style = Typography.titleMedium,
                        color = Gray3,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = swapCardData.time,
                        style = Typography.labelLarge,
                        color = Gray4,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_show),
                        contentDescription = stringResource(R.string.view_count_icon_description),
                        modifier = Modifier.height(30.dp),
                        colorFilter = ColorFilter.tint(Gray4),
                    )
                    Spacer(modifier = Modifier.size(Paddings.small))
                    Text(
                        text = swapCardData.viewCount,
                        style = Typography.labelLarge,
                        color = Gray4,
                    )
                }
            }
        }
    }
}

val swapCardData =
    SwapCardData(
        imageUri = "https://static.nike.com/a/images/c_limit",
        category = "가방",
        viewCount = "100",
        region = "강서구",
        time = "1일전",
        price = 230000,
        title = "나이키 운동화",
    )

@Preview(showBackground = true)
@Composable
fun SwapCardPreview() {
    SwapCard(
        swapCardData,
    )
}
