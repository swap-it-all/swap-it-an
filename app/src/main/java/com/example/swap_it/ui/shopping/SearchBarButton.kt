package com.example.swap_it.ui.shopping

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import com.example.swap_it.R
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray5

@Composable
fun SearchBarButton(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    Button(
        modifier = modifier.size(screenWidth / 8 * 7, screenHeight / 20),
        colors = ButtonDefaults.buttonColors(backgroundColor = Gray5),
        onClick = {},
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier.size(screenWidth / 8 * 7, screenHeight / 20),
        ) {
            Row(
                modifier = modifier.size(
                    screenWidth / 8 * 7,
                    screenHeight / 20
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "스왑에서 찾아보세요!",
                    modifier = modifier.weight(1f),
                    color = Gray3
                )
                Image(
                    painter = painterResource(R.drawable.ic_search_magnifying),
                    contentDescription = "검색 버튼",
                    colorFilter = ColorFilter.tint(Gray3)
                )
            }
        }
    }
}