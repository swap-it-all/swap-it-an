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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.swapit.data.model.ShoppingDetailData
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.component.MenuButton
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.White

@Composable
fun ProductImageSection(
    modifier: Modifier = Modifier,
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
                    modifier
                        .fillMaxWidth()
                        .height(412.dp),
                model = shoppingDetailData.imageUri[page],
                contentDescription = "상품 이미지",
                placeholder = ColorPainter(Primary),
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
                modifier
                    .align(Alignment.TopStart)
                    .padding(Paddings.largeExtra, Paddings.xlarge, Paddings.none, Paddings.none),
            navController,
            color = White,
        )
        MenuButton(
            modifier =
                modifier
                    .align(Alignment.TopEnd)
                    .padding(Paddings.none, Paddings.xlarge, Paddings.xlarge, Paddings.none),
            navController,
            White,
        )
    }
}
