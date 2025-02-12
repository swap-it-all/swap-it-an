package com.example.swap_it.ui.shopping

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.swap_it.data.model.ShoppingCardData
import com.example.swap_it.ui.theme.BackgroundColor
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.Typography
import com.example.swap_it.ui.theme.White

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
        Row(verticalAlignment = Alignment.CenterVertically) {
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
            Column(
                modifier = Modifier.padding(
                    Paddings.large,
                    Paddings.none,
                    Paddings.none,
                    Paddings.none
                )
            ) {
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
val productCardData = ShoppingCardData(
    imageUri = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/d0382875-94af-4a2f-a788-6d2ce0657de2/W+AIR+MAX+DN8.png",
    category = "가방",
    viewCount = "100",
    region = "강서구",
    time = "1일전",
    price = "10000",
    title = "나이키 운동화",
)
@Preview(showBackground = true)
@Composable
fun ShoppingCardPreview() {
    ShoppingCard(
        productCardData
    )
}