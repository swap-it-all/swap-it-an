package com.example.swap_it.ui.product_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.R
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.White

class ProductListScreen {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ProductScreen(modifier: Modifier = Modifier) {
        Scaffold(
            topBar = {
                TopAppBarCustom()
            },
            bottomBar = {
                BottomAppBarCustom()
            }
        ) {
            Surface(modifier = modifier) {
                Text(text = "Product Screen")
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarCustom(modifier: Modifier = Modifier) {
        TopAppBar(
            title = {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = "로고",
                    modifier = modifier.size(32.dp),
                )
            },
            actions = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_bell),
                        contentDescription = "알림"
                    )
                }
            }
        )
    }

    @Composable
    fun BottomAppBarCustom(modifier: Modifier = Modifier) {
        BottomAppBar(
            contentPadding = PaddingValues(horizontal = 16.dp),
            containerColor = White,
            actions = {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    BottomAppBarButton(painterResource(R.drawable.ic_house),"홈")
                    BottomAppBarButton(painterResource(R.drawable.ic_shopping_bag),"거래 목록")
                    BottomAppBarButton(painterResource(R.drawable.ic_add_plus_circle),"물건 등록")
                    BottomAppBarButton(painterResource(R.drawable.ic_chat),"채팅")
                    BottomAppBarButton(painterResource(R.drawable.ic_user),"사용자 정보")
                }

            }
        )
    }

    @Composable
    fun BottomAppBarButton(painter: Painter, contentDescription: String) {
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ProductScreenPreview() {
        SwapitTheme {
            ProductScreen()
        }
    }
}