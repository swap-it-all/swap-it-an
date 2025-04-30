package com.swapit.oopswap.ui.search

import ShoppingViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.swapit.oopswap.R
import com.swapit.oopswap.domain.repository.ProductRepository
import com.swapit.oopswap.ui.theme.BackgroundColor
import com.swapit.oopswap.ui.theme.Paddings
import com.swapit.oopswap.ui.theme.SwapitTheme
import com.swapit.oopswap.ui.theme.Typography

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: ShoppingViewModel,
) {
    Scaffold(topBar = {
        SearchAppBar(
            navController,
            viewModel.searchKeyword.value,
            onValueChange = { viewModel.writeSearch(it) },
            onValueChanged = { viewModel.search() },
        )
    }) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
            Text(
                stringResource(R.string.search_current_term),
                style = Typography.titleLarge,
                modifier =
                    Modifier.padding(
                        Paddings.xlarge,
                        Paddings.large,
                        Paddings.none,
                        Paddings.smallMedium,
                    ),
            )
            RecentTermButtonField(viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SwapitTheme {
        SearchScreen(rememberNavController(), ShoppingViewModel(ProductRepository.instance(LocalContext.current)))
    }
}
