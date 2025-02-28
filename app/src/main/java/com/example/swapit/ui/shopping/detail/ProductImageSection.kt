package com.example.swapit.ui.shopping.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailData
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.component.MenuButton
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Gray1
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray5
import com.example.swapit.ui.theme.Gray6
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.White

@Composable
fun ProductImageSection(
    shoppingDetailData: ShoppingDetailData,
    navController: NavHostController,
) {
    val pagerState =
        rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
        ) {
            shoppingDetailData.imageUri.size
        }
    Box {
        HorizontalPager(
            state = pagerState,
            modifier =
                Modifier
                    .fillMaxWidth(),
            userScrollEnabled = true,
        ) { page ->

            AsyncImage(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(412.dp),
                model = shoppingDetailData.imageUri[page].imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "상품 이미지",
                placeholder = ColorPainter(Primary),
                colorFilter = ColorFilter.tint(Gray5, blendMode = BlendMode.Darken)
            )
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) White else White.copy(alpha = 0.5f)
                Box(
                    modifier =
                        Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(6.dp),
                )
            }
        }
        BackButton(
            modifier =
                Modifier
                    .align(Alignment.TopStart)
                    .padding(Paddings.largeExtra, Paddings.xextra*2, Paddings.none, Paddings.none),
            navController,
            color = White,
        )
        MenuButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(Paddings.none, Paddings.xextra*2, Paddings.xlarge, Paddings.none),
            navController,
            White,
        )
    }
}
