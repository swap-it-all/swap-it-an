package com.example.swapit.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.swapit.domain.repository.DefaultUserRepository
import com.example.swapit.domain.repository.UserRepository
import com.example.swapit.ui.component.AppBar
import com.example.swapit.ui.component.BottomNavigationBar
import com.example.swapit.ui.theme.BackgroundColor

@Composable
fun UserInfoScreen(
    navController: NavHostController,
    viewModel: UserInfoViewModel,
) {
    val userInfo by viewModel.userInfo.collectAsState()

    LaunchedEffect(Unit) { viewModel.myUserInfo() }

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
                ProfileCard(navController = navController, userInfo = userInfo!!)
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
    UserInfoScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = UserInfoViewModel(repository = UserRepository.instance())
    )
}
