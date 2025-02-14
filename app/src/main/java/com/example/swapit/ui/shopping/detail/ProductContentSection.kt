package com.example.swapit.ui.shopping.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.swapit.R
import com.example.swapit.data.model.ShoppingDetailData
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Gray6
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography
import java.text.DecimalFormat

@Composable
fun ProductContentSection(shoppingDetailData: ShoppingDetailData) {
    TitleSection(shoppingDetailData)
    PriceSection(shoppingDetailData)
    UserInfoSection(shoppingDetailData)
    DecriptionSection(shoppingDetailData)
}

@Composable
fun TitleSection(shoppingDetailData: ShoppingDetailData) {
    Text(
        "${shoppingDetailData.category} | ${shoppingDetailData.quality} | ${shoppingDetailData.time}",
        style = Typography.labelLarge,
        color = Gray4,
        modifier =
            Modifier.padding(
                Paddings.xlarge,
                Paddings.xlarge,
                Paddings.none,
                Paddings.small,
            ),
    )
    Row(modifier = Modifier.padding(Paddings.xlarge, Paddings.none)) {
        Text(
            shoppingDetailData.title,
            style = Typography.titleLarge,
            modifier = Modifier.weight(1f),
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_show),
                contentDescription = "조회 수",
                tint = Gray4,
            )
            Spacer(Modifier.width(Paddings.small))
            Text(
                shoppingDetailData.viewCount,
                style = Typography.labelLarge,
                color = Gray4,
            )
        }
    }
}

@Composable
fun PriceSection(shoppingDetailData: ShoppingDetailData) {
    val decimal = DecimalFormat("#,###")
    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier.padding(Paddings.xlarge),
        color = Gray5,
    )
    Text(
        "판매자 예상 가격",
        style = Typography.titleLarge,
        color = Gray3,
        modifier = Modifier.padding(Paddings.xlarge, Paddings.none),
    )
    Row(modifier = Modifier.padding(Paddings.xlarge, Paddings.small)) {
        Text(decimal.format(shoppingDetailData.price), style = Typography.titleLarge)
        Text("원", style = Typography.titleLarge, color = Gray3)
    }

    HorizontalDivider(
        thickness = 10.dp,
        color = Gray6,
        modifier = Modifier.padding(Paddings.none, Paddings.xlarge),
    )
}

@Composable
fun UserInfoSection(shoppingDetailData: ShoppingDetailData) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.size(Paddings.xlarge))
        AsyncImage(
            model = shoppingDetailData.userImageUri,
            contentDescription = "판매자 이미지",
            placeholder = ColorPainter(Primary),
            modifier =
                Modifier
                    .size(40.dp)
                    .clip(CircleShape),
        )
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(Paddings.large, Paddings.none),
            ) {
                Text(shoppingDetailData.userName, style = Typography.titleSmall)
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_filled_star),
                    contentDescription = "별점",
                    tint = Primary,
                    modifier =
                        Modifier
                            .padding(start = Paddings.small)
                            .size(16.dp),
                )
                Text(
                    shoppingDetailData.rate.toString(),
                    modifier = Modifier.padding(start = Paddings.small),
                    style = Typography.labelLarge,
                )
            }
            Text(
                shoppingDetailData.region,
                modifier =
                    Modifier.padding(
                        Paddings.large,
                        Paddings.xsmall,
                        Paddings.none,
                        Paddings.none,
                    ),
                style = Typography.labelLarge,
                color = Gray4,
            )
        }
    }
}

@Composable
fun DecriptionSection(shoppingDetailData: ShoppingDetailData) {
    HorizontalDivider(
        thickness = 10.dp,
        color = Gray6,
        modifier = Modifier.padding(Paddings.none, Paddings.xlarge),
    )
    Text(
        shoppingDetailData.content,
        Modifier.padding(Paddings.xlarge, Paddings.none),
        style = Typography.bodyMedium,
    )
    Spacer(modifier = Modifier.height(148.dp))
}
