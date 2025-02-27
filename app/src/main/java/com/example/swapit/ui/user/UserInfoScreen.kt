package com.example.swapit.ui.user

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.swapit.R
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
                ProfileSwapCard(
                    userSwapStats = userInfo!!,
                )
                LazyColumn {
                    item {
                        ProfileItem(
                            text = stringResource(R.string.user_post_product),
                            count = userInfo!!.totalGoodsCount,
                        )
                        HorizontalDivider()
                        ProfileItem(
                            text = stringResource(R.string.user_swap_review),
                            count = userInfo!!.reviews.size.toLong(),
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
