package com.example.swap_it.ui.swap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swap_it.R
import com.example.swap_it.ui.component.Cards
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.Typography

@Composable
    fun RequestedSwapSection(modifier: Modifier, navController: NavHostController){
        Row(
            modifier
                .fillMaxWidth()
                .padding(Paddings.xlarge),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "스왑 요청이 들어왔어요!",
                style = Typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "요청된 스왑으로 가기"
                )
            }
        }
        LazyRow {
            items(10){
                Cards().SwapCard(swapCardData = Cards.swapCardData)
            }
        }
    }