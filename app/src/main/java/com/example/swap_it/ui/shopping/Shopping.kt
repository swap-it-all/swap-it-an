package com.example.swap_it.ui.shopping

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.swap_it.ui.component.AppBar
import com.example.swap_it.ui.component.BottomNavigationBar
import com.example.swap_it.ui.component.Cards
import com.example.swap_it.ui.component.CategoryButton
import com.example.swap_it.ui.component.SearchBarButton
import com.example.swap_it.ui.theme.BackgroundColor

import com.example.swap_it.ui.theme.Gray4
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.SwapitTheme


class Shopping {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShoppingScreen(modifier: Modifier = Modifier, navController: NavHostController) {
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                AppBar(navController = navController)
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { contentPadding ->
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                color = BackgroundColor
            ) {
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        SearchBarButton(modifier.padding(Paddings.xlarge, Paddings.small), navController)
                    }
                    item {
                        CategorySection(modifier) { showBottomSheet = true }
                    }
                    items(10) {
                        Cards().ProductListCard(Cards.productCardData)
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
fun CategorySection(modifier: Modifier, showBottomSheetOnClick: () -> Unit) {
    LazyRow(modifier = modifier.fillMaxWidth().padding(Paddings.large,Paddings.mediumLarge)) {
        item {
            IconButton(
                onClick =
                showBottomSheetOnClick
            ) {
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
        items(10) {
            CategoryButton(
                text = "카테고리",
                onClick = {},
                isSelected = true,
                modifier = modifier.padding(Paddings.small)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    SwapitTheme {
        Shopping().ShoppingScreen(Modifier, rememberNavController())
    }
}