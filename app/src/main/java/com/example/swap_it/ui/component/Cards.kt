package com.example.swap_it.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.swap_it.R
import com.example.swap_it.ui.theme.BackgroundColor
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.Typography
import com.example.swap_it.ui.theme.White


data class ShoppingCardData(
    val imageUri: String,
    val category: String,
    val viewCount: String,
    val region: String,
    val time: String,
    val price: String,
    val title: String,
    val onClick: () -> Unit = {}
)

data class AlertCardData(
    val message: String,
    val date: String,
    val icon: Int,
    val onClick: () -> Unit = {}
)

data class SwapCardData(
    val imageUri: String,
    val category: String,
    val viewCount: String,
    val region: String,
    val time: String,
    val price: String,
    val title: String,
    val onClick: () -> Unit = {}
)


class Cards {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShoppingCard(shoppingCardData: ShoppingCardData) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(Paddings.xlarge, Paddings.smallMedium),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            shape = RoundedCornerShape(20.dp),
            onClick = shoppingCardData.onClick
        ) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Spacer(modifier = Modifier.size(Paddings.large))
                AsyncImage(
                    model = shoppingCardData.imageUri,
                    contentDescription = "상품 대표 이미지",
                    modifier = Modifier
                        .size(86.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    placeholder = ColorPainter(Primary),
                    fallback = rememberVectorPainter(Icons.Default.Send),
                    error = rememberVectorPainter(Icons.Default.Settings),
                )
                Column(modifier = Modifier.padding(Paddings.large, Paddings.none, Paddings.none, Paddings.none)) {
                    Text(
                        modifier = Modifier.padding(
                            Paddings.none,
                            Paddings.largeExtra,
                            Paddings.none,
                            Paddings.xsmall
                        ),
                        text = "${shoppingCardData.category} | ${shoppingCardData.region} | ${shoppingCardData.time}",
                        style = Typography.labelLarge,
                        color = Gray4
                    )
                    Text(
                        modifier = Modifier.padding(
                            Paddings.none,
                            Paddings.none,
                            Paddings.none,
                            Paddings.small
                        ),
                        text = shoppingCardData.title,
                        style = Typography.titleMedium,
                        maxLines = 1,
                    )
                    Row(
                        modifier = Modifier.padding(
                            Paddings.none,
                            Paddings.none,
                            Paddings.extra,
                            Paddings.largeExtra
                        ), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "예상 ",
                                style = Typography.bodyMedium,
                                color = Gray3
                            )
                            Text(
                                text = shoppingCardData.price,
                                style = Typography.titleMedium,
                            )
                            Text(
                                text = "원",
                                style = Typography.bodyMedium,
                                color = Gray3
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.ic_show),
                            contentDescription = "조회 수",
                            colorFilter = ColorFilter.tint(Gray4)
                        )
                        Text(
                            text = shoppingCardData.viewCount,
                            style = Typography.labelLarge,
                            color = Gray4
                        )
                    }
                }

            }

        }
    }


    @Composable
    fun AlertCard(alertCardData: AlertCardData) {
        @OptIn(ExperimentalMaterial3Api::class)
        Card(
            modifier = Modifier.padding(Paddings.xlarge, Paddings.medium),
            colors = CardDefaults.cardColors(
                containerColor = BackgroundColor
            ),
            onClick = alertCardData.onClick
        ) {
            Column {
                Row {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(White),
                    ) {
                        Icon(
                            painter = painterResource(id = alertCardData.icon),
                            contentDescription = "알림 아이콘",
                            tint = Primary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(Paddings.medium)) {
                        Text(
                            text = alertCardData.message,
                            style = Typography.titleMedium,
                        )
                        Spacer(modifier = Modifier.height(Paddings.medium))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = alertCardData.date,
                                style = Typography.labelLarge,
                                color = Gray4
                            )
                        }
                    }
                }
                Spacer(Modifier.size(20.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Gray4)
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SwapCard(swapCardData: SwapCardData) {
        Card(
            modifier = Modifier
                .width(162.dp)
                .padding(Paddings.smallMedium),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            shape = RoundedCornerShape(20.dp),
            onClick = swapCardData.onClick
        ) {
            Column {
                AsyncImage(
                    model = swapCardData.imageUri,
                    contentDescription = "상품 대표 이미지",
                    modifier = Modifier
                        .size(150.dp),
                    placeholder = ColorPainter(Primary),
                    fallback = rememberVectorPainter(Icons.Default.Send),
                    error = rememberVectorPainter(Icons.Default.Settings),
                )
                Column(modifier = Modifier.padding(Paddings.large)) {
                    Text(
                        text = "${swapCardData.category} | ${swapCardData.region}",
                        style = Typography.labelLarge,
                        color = Gray4
                    )
                    Text(
                        text = swapCardData.title,
                        style = Typography.titleMedium,
                        maxLines = 1,
                        modifier = Modifier.padding(Paddings.none,Paddings.none,Paddings.none,Paddings.small)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(Paddings.none,Paddings.none,Paddings.none,Paddings.large)
                    ) {
                        Text(
                            text = "예상 ",
                            style = Typography.titleMedium,
                            color = Gray3
                        )
                        Text(
                            text = swapCardData.price,
                            style = Typography.titleMedium,
                        )
                        Text(
                            text = "원",
                            style = Typography.titleMedium,
                            color = Gray3
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = swapCardData.time,
                            style = Typography.labelLarge,
                            color = Gray4
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_show),
                            contentDescription = "조회 수",
                            modifier = Modifier.height(30.dp),
                            colorFilter = ColorFilter.tint(Gray4)
                        )
                        Spacer(modifier = Modifier.size(Paddings.small))
                        Text(
                            text = swapCardData.viewCount,
                            style = Typography.labelLarge,
                            color = Gray4
                        )
                    }

                }

            }

        }
    }


    companion object {
        val productCardData = ShoppingCardData(
            imageUri = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/d0382875-94af-4a2f-a788-6d2ce0657de2/W+AIR+MAX+DN8.png",
            category = "가방",
            viewCount = "100",
            region = "강서구",
            time = "1일전",
            price = "10000",
            title = "나이키 운동화",
        )
        val alertCardData = AlertCardData(
            message = "스왑 요청이 들어왔어요",
            date = "2월 19일 13:22",
            icon = R.drawable.ic_show
        )
        val swapCardData = SwapCardData(
            imageUri = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/d0382875-94af-4a2f-a788-6d2ce0657de2/W+AIR+MAX+DN8.png",
            category = "가방",
            viewCount = "100",
            region = "강서구",
            time = "1일전",
            price = "10000",
            title = "나이키 운동화",
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ShoppingCardPreview() {
    Cards().ShoppingCard(
        Cards.productCardData
    )
}

@Preview(showBackground = true)
@Composable
fun AlertCardPreview() {
    Cards().AlertCard(
        Cards.alertCardData
    )
}

@Preview(showBackground = true)
@Composable
fun SwapCardPreview() {
    Cards().SwapCard(
        Cards.swapCardData
    )
}