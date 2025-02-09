package com.example.swap_it.ui.product_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.ui.component.BackButton
import com.example.swap_it.ui.component.SearchTermButton
import com.example.swap_it.ui.theme.BackgroundColor
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.Typography

class Search {
    enum class CurrentSearch(val categoryName: String) {
        CAR("미미"),
        CLOTH("부족해"),
        MOBILE("가방"),
        CUP("나같은"),
        BOOK("없다는"),
        ELECTRONIC("걸"),
        FASHION("아니"),
        FOOD("시간"),
        ETC("이"),
        SNACK("흐른"),
    }

    enum class PopularSearch(val categoryName: String) {
        CAR("참기 힘들었어"),
        CLOTH("내 맘을 보여주면"),
        MOBILE("변할지 난 궁금해"),
        CUP("이 바보같은 모습을"),
        BOOK("참 많이 부족하지만"),
        ELECTRONIC("나 같은 남잔 없다는 걸 아니"),
        FASHION("많이 부족하겠지만"),
        FOOD("세월이 흘러가도"),
        ETC("너의 작은 실수라도"),
        SNACK("많이"),
    }

    @Composable
    fun SearchScreen(modifier: Modifier = Modifier, navController: NavHostController) {
        var searchTerm by remember { mutableStateOf("") }
        Column(
            modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            Spacer(Modifier.size(32.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        Paddings.none, Paddings.none, Paddings.none, Paddings.xlarge
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(modifier.padding(Paddings.medium),navController)
                SearchField(
                    searchTerm,
                    modifier,
                    onValueChange = {
                        searchTerm = it
                    }
                )
            }
            Text(
                "최근 검색어",
                style = Typography.titleLarge,
                modifier = Modifier.padding(
                    Paddings.xlarge,
                    Paddings.large,
                    Paddings.none,
                    Paddings.smallMedium
                )
            )
            CurrentTermButtonField()
            Text(
                "인기 검색어",
                style = Typography.titleLarge,
                modifier = Modifier.padding(
                    Paddings.xlarge,
                    Paddings.xextra,
                    Paddings.none,
                    Paddings.smallMedium
                )
            )
            PopularTermButtonField()
        }

    }


    @Composable
    fun SearchField(
        searchTerm: String,
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit = {}
    ) {
        OutlinedTextField(
            value = searchTerm,
            onValueChange = onValueChange,
            label = {
                Text("스왑에서 찾아보세요!", style = Typography.bodyMedium)
            },
            shape = ButtonDefaults.outlinedShape,
            modifier = modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Color.Black,
                focusedBorderColor = Gray6,
                unfocusedBorderColor = Gray6,
                cursorColor = Gray6,
                backgroundColor = Gray6
            ),
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_search_magnifying),
                    contentDescription = "검색 버튼",
                    colorFilter = ColorFilter.tint(Gray3)
                )
            }

        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun CurrentTermButtonField() {
        FlowRow(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.mediumLarge),
        ) {
            CurrentSearch.entries.forEach {
                SearchTermButton(
                    text = it.categoryName,
                    modifier = Modifier.padding(Paddings.smallMedium)
                ) {

                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun PopularTermButtonField() {
        FlowRow(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.mediumLarge),
        ) {
            PopularSearch.entries.forEach {
                SearchTermButton(
                    text = it.categoryName,
                    modifier = Modifier.padding(Paddings.smallMedium)
                ) {

                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SearchScreenPreview() {
        SwapitTheme {
            Search().SearchScreen(Modifier, rememberNavController())
        }
    }
}

