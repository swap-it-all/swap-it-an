package com.example.swap_it.ui.product_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.R
import com.example.swap_it.ui.component.Cards
import com.example.swap_it.ui.component.CategoryButton
import com.example.swap_it.ui.theme.Gray5
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.SwapitTheme

class ProductListScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun ProductListScreen(modifier: Modifier = Modifier) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp
        Surface(modifier = modifier.fillMaxSize(), color = Gray6) {
            LazyColumn (horizontalAlignment = Alignment.CenterHorizontally){
                // Use item to add a single composable to the list
                item {
                    Spacer(modifier = modifier.size(10.dp))
                }
                item {
                    Button(
                        modifier = modifier.size(screenWidth / 8 * 7, screenHeight / 20),
                        colors = ButtonDefaults.buttonColors(containerColor = Gray5),
                        onClick = {},
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            modifier = modifier.size(screenWidth / 8 * 7, screenHeight / 20),
                        ) {
                            Row(
                                modifier = modifier.size(screenWidth / 8 * 7, screenHeight / 20),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("스왑에서 찾아보세요!", modifier = modifier.weight(1f), color = Color.Black)
                                Image(
                                    painter = painterResource(R.drawable.ic_search_magnifying),
                                    contentDescription = "검색 버튼"
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = modifier.size(10.dp))
                }
                item {
                    LazyRow(modifier = modifier.size(screenWidth, screenHeight / 20)) {
                        item {
                            IconButton(onClick = {}) {
                                Image(
                                    painter = painterResource(R.drawable.ic_slider),
                                    contentDescription = "필터 버튼"
                                )
                            }
                        }
                        items(2){
                            CategoryButton(text = "카테고리", onClick = {}, isSelected = true,modifier = modifier.padding(4.dp))
                        }
                        items(4){
                            CategoryButton(text = "카테고리", onClick = {}, isSelected = false,modifier = modifier.padding(4.dp))
                        }

                    }
                }
                // Use items to add multiple composables to the list
                items(10) {
                    Cards().ListCard(Cards.cardData)
                }
                item {
                    Spacer(modifier = modifier.size(100.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    SwapitTheme {
        ProductListScreen().ProductListScreen()
    }
}