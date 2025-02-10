package com.example.swap_it.ui.shopping

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.swap_it.R
import com.example.swap_it.ui.component.CategoryButton
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Paddings

@Composable
fun CategorySection(modifier: Modifier, showBottomSheetOnClick: () -> Unit) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(Paddings.large, Paddings.smallMedium)
    ) {
        item {
            IconButton(
                onClick =
                showBottomSheetOnClick
            ) {
                Box(
                    modifier = modifier
                        .size(32.dp)
                        .border(
                            BorderStroke(2.dp, Gray4),
                            shape = RoundedCornerShape(50.dp),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_slider),
                        contentDescription = "필터 버튼",
                        colorFilter = ColorFilter.tint(Gray4)
                    )
                }
            }
        }
        items(10) {
            CategoryButton(
                text = "카테고리",
                onClick = {},
                isSelected = true,
                modifier = modifier.padding(Paddings.small)
            )
        }

    }
}