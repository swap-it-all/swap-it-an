package com.example.swap_it.ui.product_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.ui.component.SearchTermButton
import com.example.swap_it.ui.theme.BackgroundColor
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.Typography

class SearchScreen {
    enum class CurrentSearch(val categoryName: String) {
        CAR("자동차"),
        CLOTH("옷"),
        MOBILE("모바일 기기"),
        CUP("컵"),
        BOOK("책"),
        ELECTRONIC("가전기기"),
        FASHION("패션"),
        FOOD("음식"),
        ETC("기타"),
        SNACK("과자"),
    }

    enum class PopularSearch(val categoryName: String) {
        CAR("자동차"),
        CLOTH("옷"),
        MOBILE("모바일 기기"),
        CUP("컵"),
        BOOK("책"),
        ELECTRONIC("가전기기"),
        FASHION("패션"),
        FOOD("음식"),
        ETC("기타"),
        SNACK("과자"),
    }

    @OptIn(ExperimentalLayoutApi::class)
    @SuppressLint("NotConstructor", "UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun SearchScreen(
        modifier: Modifier = Modifier,
        navController: NavHostController,
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp
        var searchTerm by remember { mutableStateOf("") }
        Surface(modifier = modifier.fillMaxSize(), color = BackgroundColor) {
            Column {
                Spacer(Modifier.size(32.dp))
                Row {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_chevron_left),
                            contentDescription = "뒤로 가기",
                        )
                    }
//
                    OutlinedTextField(
                        value = searchTerm,
                        onValueChange = { searchTerm = it },
                        label = {
                            Text("스왑에서 찾아보세요!")
                        },
                        shape = ButtonDefaults.outlinedShape,
                        modifier = modifier.size(screenWidth / 5 * 4, screenHeight / 16),
                        colors =
                            TextFieldDefaults.outlinedTextFieldColors(
                                focusedLabelColor = Color.Black,
                                focusedBorderColor = Gray6,
                                unfocusedBorderColor = Gray6,
                                cursorColor = Gray6,
                                backgroundColor = Gray6,
                            ),
                        trailingIcon = {
                            Image(
                                painter = painterResource(R.drawable.ic_search_magnifying),
                                contentDescription = "검색 버튼",
                                colorFilter = ColorFilter.tint(Gray3),
                            )
                        },
                    )
                }

                Text(
                    "최근 검색어",
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(Paddings.large),
                )
                FlowRow(
                    horizontalArrangement = Arrangement.Start,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                ) {
                    CurrentSearch.entries.forEach {
                        SearchTermButton(
                            text = it.categoryName,
                            modifier = Modifier.padding(Paddings.small),
                        ) {
                        }
                    }
                }

                Text(
                    "인기 검색어",
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(Paddings.large),
                )
                FlowRow(
                    horizontalArrangement = Arrangement.Start,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                ) {
                    PopularSearch.entries.forEach {
                        SearchTermButton(
                            text = it.categoryName,
                            modifier = Modifier.padding(Paddings.small),
                        ) {
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SearchScreenPreview() {
        SwapitTheme {
            SearchScreen().SearchScreen(Modifier, rememberNavController())
        }
    }
}
