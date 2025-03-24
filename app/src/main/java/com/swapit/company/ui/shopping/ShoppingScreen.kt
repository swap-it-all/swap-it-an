package com.swapit.company.ui.shopping

import ShoppingViewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.swapit.company.domain.repository.ProductRepository
import com.swapit.company.ui.component.AppBar
import com.swapit.company.ui.component.BottomNavigationBar
import com.swapit.company.ui.navigation.NavItem
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.SwapitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    navController: NavHostController,
    viewModel: ShoppingViewModel,
) {
    val sheetState = rememberModalBottomSheetState()
    viewModel.fetchProducts()
    Scaffold(
        topBar = {
            AppBar(navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { contentPadding ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            color = BackgroundColor,
        ) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    SearchBarButton(
                        Modifier.padding(Paddings.xlarge, Paddings.smallMedium),
                        navController,
                        viewModel = viewModel,
                    )
                }
                item {
                    CategorySection(Modifier) { viewModel.showBottomSheet() }
                }
                itemsIndexed(
                    items = viewModel.products,
                    key = { _, item -> item.goodsId },
                ) { _, item ->
                    ShoppingCard(item) {
                        navController.navigate(NavItem.ShoppingDetail.screenRoute + "/${item.goodsId}")
                    }
                }
                item {
                    Spacer(modifier = Modifier.size(100.dp))
                }
            }
            if (viewModel.bottomSheet.value) {
                ModalBottomSheet(
                    onDismissRequest = {
                        viewModel.dismissBottomSheet()
                    },
                    sheetState = sheetState,
                ) {
                    ShoppingSortBottomSheetContent(viewModel = viewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    SwapitTheme {
        ShoppingScreen(rememberNavController(), ShoppingViewModel(ProductRepository.instance(LocalContext.current)))
    }
}
