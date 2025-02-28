package com.example.swapit.ui.shopping

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(Paddings.large, Paddings.smallMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(onClick = showBottomSheetOnClick) {
            Row {
                Text("정렬 옵션")// TODO: 넣은거 나오게
                Icon(
                    painter = painterResource(R.drawable.ic_caret_down),
                    tint = Gray4,
                    contentDescription = "정렬"
                )
            }
        }
        TextButton(onClick = showBottomSheetOnClick) {
            Row {
                Text("카테고리 ") // TODO: 숫자 나오게
                Icon(
                    painter = painterResource(R.drawable.ic_caret_down),
                    tint = Gray4,
                    contentDescription = "정렬"
                )
            }
        }
    }
}
