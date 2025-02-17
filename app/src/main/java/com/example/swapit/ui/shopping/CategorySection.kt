package com.example.swapit.ui.shopping

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.ui.component.CategoryButton
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings

@Composable
fun CategorySection(
    modifier: Modifier,
    showBottomSheetOnClick: () -> Unit,
) {
    LazyRow(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(Paddings.large, Paddings.smallMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        item {
            IconButton(
                onClick =
                showBottomSheetOnClick,
            ) {
                Box(
                    modifier =
                        modifier
                            .size(32.dp)
                            .border(
                                BorderStroke(2.dp, Gray4),
                                shape = RoundedCornerShape(50.dp),
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_slider),
                        contentDescription = stringResource(R.string.shopping_filter_description),
                        colorFilter = ColorFilter.tint(Gray4),
                    )
                }
            }
        }
        items(10) {
            CategoryButton(
                text = stringResource(R.string.shopping_category_title),
                onClick = {},
                isSelected = true,
                modifier = modifier.padding(Paddings.small),
            )
        }
    }
}
