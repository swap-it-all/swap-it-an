package com.swapit.oopswap.ui.shopping

import ShoppingViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.swapit.oopswap.R
import com.swapit.oopswap.ui.theme.Black
import com.swapit.oopswap.ui.theme.Gray3
import com.swapit.oopswap.ui.theme.Gray4
import com.swapit.oopswap.ui.theme.Paddings

@Composable
fun CategorySection(
    modifier: Modifier,
    showBottomSheetOnClick: () -> Unit,
    viewModel: ShoppingViewModel,
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
                if (viewModel.selectedOption.value == null) {
                    Text("정렬", color = Gray3)
                } else {
                    Text(viewModel.selectedOption.value!!.option, color = Black)
                }

                Icon(
                    painter = painterResource(R.drawable.ic_caret_down),
                    tint = Gray4,
                    contentDescription = "정렬",
                )
            }
        }
        TextButton(onClick = showBottomSheetOnClick) {
            Row {
                if (viewModel.selectedCategory.value.isEmpty()) {
                    Text("카테고리", color = Gray3)
                } else {
                    Text("카테고리 ${viewModel.selectedCategory.value.size}", color = Black)
                }
                Icon(
                    painter = painterResource(R.drawable.ic_caret_down),
                    tint = Gray4,
                    contentDescription = "정렬",
                )
            }
        }
    }
}
