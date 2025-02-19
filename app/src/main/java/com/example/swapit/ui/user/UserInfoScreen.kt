package com.example.swapit.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.swapit.ui.component.AppBar
import com.example.swapit.ui.component.BottomNavigationBar
import com.example.swapit.ui.theme.BackgroundColor

@Composable
fun UserInfoScreen(navController: NavHostController) {
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
            Column {
                ProfileCard()
                ProfileSwapCard()
                LazyColumn {
                    item {
                        ProfileItem(
                            text = "내가 등록한 물건",
                            count = 5,
                        )
                        HorizontalDivider()
                        ProfileItem(
                            text = "받은 스왑 리뷰",
                            count = 11,
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoScreenPreview() {
    UserInfoScreen(navController = NavHostController(LocalContext.current))
}
