package com.example.swap_it.ui.product_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.swap_it.R
import com.example.swap_it.ui.component.Cards.Companion.cardData
import com.example.swap_it.ui.component.ListCardData
import com.example.swap_it.ui.theme.Background
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Gray5
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.Typography
import com.example.swap_it.ui.theme.White

data class AlertCardData(
    val message: String,
    val date: String,
    val icon: Int,
    val onClick: () -> Unit = {}
)

class AlertScreen {

    @SuppressLint("NotConstructor", "RestrictedApi")
    @Composable
    fun AlertScreen(modifier: Modifier = Modifier,navController: NavHostController) {
        Surface(modifier = modifier.fillMaxSize(), color = Background) {
            Column {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(Paddings.medium)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_left),
                        contentDescription = "뒤로 가기"
                    )
                }
                Text(
                    "알림",
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(Paddings.large)
                )
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    items(4) {
                        AlertListCard(alertCardData = cardData)
                    }


                }

            }

        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AlertListCard(alertCardData: AlertCardData) {
        Card(
            modifier = Modifier.padding(Paddings.xlarge, Paddings.medium),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = Background
            ),
            onClick = alertCardData.onClick
        ) {
            Column {


                Row {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(White),
                    ) {
                        Icon(
                            painter = painterResource(id = alertCardData.icon),
                            contentDescription = "알림 아이콘",
                            tint = Primary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(Paddings.medium)) {
                        Text(
                            text = alertCardData.message,
                            style = Typography.titleMedium,
                        )
                        Spacer(modifier = Modifier.height(Paddings.medium))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = alertCardData.date,
                                style = Typography.labelLarge,
                                color = Gray4
                            )
                        }
                    }


                }
                Spacer(Modifier.size(12.dp))
                Box(Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray5))
            }


        }
    }

    companion object {
        val cardData = AlertCardData(
            message = "스왑 요청이 들어왔어요",
            date = "2월 19일 13:22",
            icon = R.drawable.ic_show
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    var navController = rememberNavController()
    SwapitTheme {
        AlertScreen().AlertScreen(Modifier,navController)
    }
}