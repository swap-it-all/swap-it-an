package com.swapit.company.ui.shopping.detail

import android.annotation.SuppressLint
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.swapit.company.R
import com.swapit.company.ui.component.AlertDialog
import com.swapit.company.ui.component.BackButton
import com.swapit.company.ui.navigation.NavItem
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Gray5
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Primary
import com.swapit.company.ui.theme.White
import com.swapit.company.ui.user.UserInfoViewModel

@Composable
fun ProductImageSection(
    shoppingDetailViewModel: ShoppingDetailViewModel,
    navController: NavHostController,
    uerInfoViewModel: UserInfoViewModel,
) {
    val shoppingDetailData = shoppingDetailViewModel.detailContents
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
                colorFilter = ColorFilter.tint(Gray5, blendMode = BlendMode.Darken),
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
                    .padding(Paddings.largeExtra, Paddings.xextra * 2, Paddings.none, Paddings.none),
            navController,
            color = White,
        )
        EditDeleteAccuseDropdownMenu(
            modifier = Modifier.align(Alignment.TopEnd),
            shoppingDetailViewModel,
            uerInfoViewModel,
            navController,
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditDeleteAccuseDropdownMenu(
    modifier: Modifier = Modifier,
    shoppingDetailViewModel: ShoppingDetailViewModel,
    viewModel: UserInfoViewModel,
    navController: NavHostController,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val horizontalPadding = screenWidthDp.value
    val shoppingDetailData = shoppingDetailViewModel.detailContents
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            modifier =
                modifier.padding(Paddings.none, Paddings.xextra * 2 - 12.dp, Paddings.largeExtra, Paddings.none).align(Alignment.TopEnd),
            onClick = { shoppingDetailViewModel.dropMenuExpanded.value = !shoppingDetailViewModel.dropMenuExpanded.value },
        ) {
            Icon(
                painterResource(R.drawable.ic_menu),
                contentDescription = "Dropdown Menu",
                tint = White,
            )
        }
        if (shoppingDetailData.user.userId == viewModel.userInfo.value?.id) {
            DropdownMenu(
                offset =
                    DpOffset(
                        x = horizontalPadding.dp,
                        y = 0.dp,
                    ),
                containerColor = BackgroundColor,
                expanded = shoppingDetailViewModel.dropMenuExpanded.value,
                onDismissRequest = { shoppingDetailViewModel.dropMenuExpanded.value = false },
            ) {
                DropdownMenuItem(
                    text = { Text("수정하기") },
                    onClick = {
                        shoppingDetailViewModel.dropMenuExpanded.value = false
                    },
                )
                DropdownMenuItem(
                    text = { Text("삭제하기") },
                    onClick = {
                        shoppingDetailViewModel.showDeleteDialog.value = true
                        shoppingDetailViewModel.dropMenuExpanded.value = false
                    },
                )
            }
        } else {
            DropdownMenu(
                offset =
                    DpOffset(
                        x = horizontalPadding.dp,
                        y = 0.dp,
                    ),
                containerColor = BackgroundColor,
                expanded = shoppingDetailViewModel.dropMenuExpanded.value,
                onDismissRequest = { shoppingDetailViewModel.dropMenuExpanded.value = false },
            ) {
                DropdownMenuItem(
                    text = { Text("신고하기") },
                    onClick = {
                        navController.navigate(NavItem.Report.screenRoute + "/${shoppingDetailData.goodsId}")
                        shoppingDetailViewModel.dropMenuExpanded.value = false
                    },
                )
            }
        }
        if (shoppingDetailViewModel.showDeleteDialog.value) {
            AlertDialog(
                title = "상품을 삭제하시겠습니까?",
                description = "삭제된 상품은 복구할 수 없습니다.",
                cancelText = "취소",
                confirmText = "삭제",
                onClickCancel = {
                    shoppingDetailViewModel.showDeleteDialog.value = false
                },
                onClickConfirm = {
                    shoppingDetailViewModel.deleteProduct()
                    shoppingDetailViewModel.showDeleteDialog.value = false
                    navController.popBackStack()
                },
            )
        }
    }
}
