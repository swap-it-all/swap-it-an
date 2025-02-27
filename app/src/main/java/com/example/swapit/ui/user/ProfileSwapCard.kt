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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.domain.model.user.UserSwapStats
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

@Composable
fun ProfileSwapCard(
    userSwapStats: UserSwapStats,
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
                Text(
                    userSwapStats.totalGoodsCount.toString(),
                    style = Typography.headlineSmall,
                    color = Primary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.user_post_product_count),
                    style = Typography.labelLarge,
                    color = Gray4
                )
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
                Text(
                    userSwapStats.completedSwapCount.toString(),
                    style = Typography.headlineSmall,
                    color = Primary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.user_swap_count),
                    style = Typography.labelLarge,
                    color = Gray4
                )
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
                Text(
                    userSwapStats.ratingAverage.toString(),
                    style = Typography.headlineSmall,
                    color = Primary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.user_review_average),
                    style = Typography.labelLarge,
                    color = Gray4
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSwapCardPreview() {
    ProfileSwapCard(
        userSwapStats = UserSwapStats(
            totalGoodsCount = 0,
            completedSwapCount = 0,
            ratingAverage = 0.0
        )
    )
}
