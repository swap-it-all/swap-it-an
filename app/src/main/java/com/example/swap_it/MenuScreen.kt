package com.example.swap_it

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.ui.component.BottomAppBarButton
import com.example.swap_it.ui.product_list.ProductListScreen
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.White

class MenuScreen {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NotConstructor")
    @Composable
    fun MenuScreen(modifier: Modifier = Modifier) {
        Scaffold(
            topBar = {
                TopAppBarCustom()
            },
            bottomBar = {
                BottomAppBarCustom()
            }
        ) {
            Column {
                Spacer(modifier = Modifier.size(64.dp))
                ProductListScreen().ProductListScreen()
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



    @Preview(showBackground = true)
    @Composable
    fun ProductScreenPreview() {
        SwapitTheme {
            MenuScreen()
        }
    }
}