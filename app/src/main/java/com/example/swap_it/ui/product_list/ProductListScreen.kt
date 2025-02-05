package com.example.swap_it.ui.product_list

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.ui.component.Cards
import com.example.swap_it.ui.component.CategoryButton
import com.example.swap_it.ui.menu.MenuScreen
import com.example.swap_it.ui.menu.MenuScreen.NavigationModule
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Gray5
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.Primary
import com.example.swap_it.ui.theme.SwapitTheme
import com.example.swap_it.ui.theme.Typography


class ProductListScreen {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("NotConstructor", "UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ProductListScreen(modifier: Modifier = Modifier, navController: NavHostController) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }

        val navigationModule = NavigationModule()
        Scaffold(
            topBar = {
                MenuScreen().AppBar(navController = navController)
            },
            bottomBar = {
                navigationModule.BottomNavigationBar(navController)
            }
        ) {
            Surface(modifier = modifier.fillMaxSize(), color = Gray6) {
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        Spacer(modifier = modifier.size(72.dp))
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
                    item {
                        Spacer(modifier = modifier.size(10.dp))
                    }
                    item {
                        LazyRow(modifier = modifier.size(screenWidth, screenHeight / 20)) {
                            item {
                                IconButton(onClick = {
                                    showBottomSheet = true
                                }) {
                                    Box(
                                        modifier = modifier
                                            .size(32.dp)
                                            .border(
                                                BorderStroke(2.dp, Gray4),
                                                shape = RoundedCornerShape(50.dp),
                                            ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.ic_slider),
                                            contentDescription = "필터 버튼",
                                            colorFilter = ColorFilter.tint(Gray4)
                                        )
                                    }
                                }
                            }
                            items(2) {
                                CategoryButton(
                                    text = "카테고리",
                                    onClick = {},
                                    isSelected = true,
                                    modifier = modifier.padding(Paddings.small)
                                )
                            }
                            items(4) {
                                CategoryButton(
                                    text = "카테고리",
                                    onClick = {},
                                    isSelected = false,
                                    modifier = modifier.padding(Paddings.small)
                                )
                            }

                        }
                    }

                    items(10) {
                        Cards().ProductListCard(Cards.cardData)
                    }
                    item {
                        Spacer(modifier = modifier.size(100.dp))
                    }
                }
                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState
                    ) {
                        ModalBottomSheetContent()
                    }
                }
            }
        }
    }

}

@Composable
fun ModalBottomSheetContent() {
    var option = SortOption.POPULAR
    Column(modifier = Modifier.padding(Paddings.large)) {
        Text("정렬", style = Typography.titleLarge)
        SortButtons(onSortOptionSelected = { option = it })
        Text("카테고리", style = Typography.titleLarge)
        CategoryButtons(option)
        Spacer(modifier = Modifier.size(48.dp))
    }

}

enum class SortOption(val option: String) {
    POPULAR("인기순"),
    RECENT("최신순"),
    PRICE_HIGH("가격 높은 순"),
    PRICE_ROW("가격 낮은 순"),
}

@Composable
fun SortButtons(
    initialSortOption: SortOption = SortOption.POPULAR,
    onSortOptionSelected: (SortOption) -> Unit
) {
    var selectedOption by remember { mutableStateOf(initialSortOption) }
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        SortOption.entries.forEach {
            SortButton(
                text = it.option,
                isSelected = selectedOption == it,
                onClick = {
                    selectedOption = it
                    onSortOptionSelected(it)
                }
            )
        }
    }
}


@Composable
fun SortButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isSelected) Primary else Gray5
    TextButton(onClick = onClick) {
        Row {
            if (isSelected) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = "선택",
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(text = text, color = textColor, style = Typography.bodySmall)
        }
    }

}

enum class CategoryOption(val categoryName: String) {
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
@Composable
fun CategoryButtons(option: SortOption) {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Paddings.medium),

        ) {
        CategoryOption.entries.forEach {
            CategoryButton(
                text = it.categoryName,
                isSelected = true,
                modifier = Modifier.padding(Paddings.small)
            ) {

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    SwapitTheme {
        ProductListScreen().ProductListScreen(Modifier,rememberNavController())
    }
}