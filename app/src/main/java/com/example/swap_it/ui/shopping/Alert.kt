package com.example.swap_it.ui.shopping

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.ui.component.Cards
import com.example.swap_it.ui.component.Cards.Companion.alertCardData
import com.example.swap_it.ui.theme.Gray4
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

class Alert {

    @SuppressLint("NotConstructor", "RestrictedApi")
    @Composable
    fun AlertScreen(modifier: Modifier = Modifier,navController: NavHostController) {
        Surface(modifier = modifier.fillMaxSize(), color = Gray6) {
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
                        Cards().AlertListCard(alertCardData = Cards.alertCardData)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    var navController = rememberNavController()
    SwapitTheme {
        Alert().AlertScreen(Modifier,navController)
    }
}