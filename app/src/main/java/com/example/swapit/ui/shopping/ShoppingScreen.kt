package com.example.swapit.ui.shopping

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.ui.component.AppBar
import com.example.swapit.ui.component.BottomNavigationBar
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.SwapitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(navController: NavHostController) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

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
                    )
                }
                item {
                    CategorySection(Modifier) { showBottomSheet = true }
                }
                items(10) {
                    ShoppingCard(productCardData, navController)
                }
                item {
                    Spacer(modifier = Modifier.size(100.dp))
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                ) {
                    ModalBottomSheetContent()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    SwapitTheme {
        ShoppingScreen(rememberNavController())
    }
}
