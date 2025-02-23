package com.example.swapit.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

@Composable
fun ProfileSwapCard(
    productCount: Int = 22,
    swapCount: Int = 16,
    review: Float = 4.8f,
) {
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
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier =
                    Modifier
                        .height(80.dp)
                        .width(127.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(productCount.toString(), style = Typography.headlineSmall, color = Primary)
                Spacer(modifier = Modifier.size(8.dp))
                Text("물건 수", style = Typography.labelLarge, color = Gray4)
            }
            VerticalDivider(
                modifier = Modifier.height(60.dp),
            )
            Column(
                modifier =
                    Modifier
                        .height(80.dp)
                        .width(127.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(swapCount.toString(), style = Typography.headlineSmall, color = Primary)
                Spacer(modifier = Modifier.size(8.dp))
                Text("스왑 수", style = Typography.labelLarge, color = Gray4)
            }
            VerticalDivider(
                modifier = Modifier.height(60.dp),
            )
            Column(
                modifier =
                    Modifier
                        .height(80.dp)
                        .width(127.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(review.toString(), style = Typography.headlineSmall, color = Primary)
                Spacer(modifier = Modifier.size(8.dp))
                Text("리뷰 평점", style = Typography.labelLarge, color = Gray4)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSwapCardPreview() {
    ProfileSwapCard()
}
