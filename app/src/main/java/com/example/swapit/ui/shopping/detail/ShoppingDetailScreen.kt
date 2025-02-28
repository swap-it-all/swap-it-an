package com.example.swapit.ui.shopping.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailData
import com.example.swapit.ui.theme.Paddings

@Composable
fun ShoppingDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ShoppingDetailViewModel,
) {
    Box(modifier.fillMaxSize()) {
        DetailContent(navController, viewModel.detailContents)
        Row(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(Paddings.xlarge, 40.dp),
        ) {
            BottomButtonSection(navController)
        }
    }
}

@Composable
fun DetailContent(
    navController: NavHostController,
    shoppingDetailData: ShoppingDetailData,
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        ProductImageSection(shoppingDetailData, navController)
        ProductContentSection(shoppingDetailData)
    }
}

@Composable
@Preview(showBackground = true)
fun ShoppingDetailScreenPreview() {
    ShoppingDetailScreen(
        navController = rememberNavController(),
        viewModel = viewModel<ShoppingDetailViewModel>()
    )
}
