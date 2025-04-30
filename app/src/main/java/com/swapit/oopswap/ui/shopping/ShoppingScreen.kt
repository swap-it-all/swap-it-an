package com.swapit.oopswap.ui.shopping

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.oopswap.ui.alert.AlertViewModel
import com.swapit.oopswap.ui.component.AppBar
import com.swapit.oopswap.ui.component.BottomNavigationBar
import com.swapit.oopswap.ui.navigation.NavItem
import com.swapit.oopswap.ui.theme.BackgroundColor
import com.swapit.oopswap.ui.theme.Paddings
import com.swapit.oopswap.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    navController: NavHostController,
    viewModel: ShoppingViewModel,
    alertViewModel: AlertViewModel,
    application: android.app.Application,
) {
    val sheetState = rememberModalBottomSheetState()
    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
        alertViewModel.fetchAlertList()
        alertViewModel.connectAndMonitor()
        alertViewModel.alertSettingInfo()
        alertViewModel.fcmRestore(application = application)
    }
    Scaffold(
        topBar = {
            AppBar(navController = navController, alertCount = alertViewModel.alertList.value.size)
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
                    CategorySection(Modifier, { viewModel.showBottomSheet() }, viewModel)
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
                    containerColor = White,
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
