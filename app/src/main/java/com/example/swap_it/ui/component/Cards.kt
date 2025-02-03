package com.example.swap_it.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.swap_it.R
import com.example.swap_it.ui.theme.Gray2
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Gray5
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.White


data class ListCardData(
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
    fun ListCard(listCardData: ListCardData) {
        Card(
            modifier = Modifier.padding(Paddings.xlarge,Paddings.medium),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = White
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = listCardData.onClick
        ) {
            Row {
                AsyncImage(
                    model = listCardData.imageUri,
                    contentDescription = "상품 대표 이미지",
                    modifier = Modifier.size(100.dp).clip(RoundedCornerShape(4.dp)),
                    placeholder = ColorPainter(Primary),
                    fallback = rememberVectorPainter(Icons.Default.Send),
                    error = rememberVectorPainter(Icons.Default.Settings),
                )
                Column(modifier = Modifier.padding(Paddings.medium)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = listCardData.category,
                            style = MaterialTheme.typography.labelLarge,
                            color = Gray4
                        )
                        Text(" | ",color = Gray4)
                        Text(
                            text = listCardData.region,
                            style = MaterialTheme.typography.labelLarge,
                            color = Gray4
                        )
                        Text(" | ",color = Gray4)
                        Text(
                            text = listCardData.time,
                            style = MaterialTheme.typography.labelLarge,
                            color = Gray4
                        )
                    }
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = listCardData.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Row (modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = "예상 ",
                                style = MaterialTheme.typography.titleMedium,
                                color = Gray3
                            )
                            Text(
                                text = listCardData.price,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = "원",
                                style = MaterialTheme.typography.titleMedium,
                                color = Gray3
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.ic_show),
                            contentDescription = "조회 수",
                            modifier = Modifier.height(30.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Gray4)
                        )
                        Spacer(modifier = Modifier.size(Paddings.small))
                        Text(
                            text = listCardData.viewCount,
                            style = MaterialTheme.typography.labelLarge,
                            color = Gray4
                        )
                    }
                }

            }

        }
    }

    companion object {
        val cardData = ListCardData(
            imageUri = "https://velog.velcdn.com/images/deepblue/post/f657fa91-f059-4e8d-81c3-c7c7776e0d9f/image.png",
            category = "가방",
            viewCount = "100",
            region = "강서구",
            time = "1일전",
            price = "10000",
            title = "가방 팔아요~~~",
        )
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Cards().ListCard(
        Cards.cardData
    )
}