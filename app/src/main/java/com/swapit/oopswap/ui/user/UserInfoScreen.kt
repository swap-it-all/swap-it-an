package com.swapit.oopswap.ui.user

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
import com.swapit.oopswap.R
import com.swapit.oopswap.domain.repository.UserRepository
import com.swapit.oopswap.ui.component.BottomNavigationBar
import com.swapit.oopswap.ui.theme.BackgroundColor

@Composable
fun UserInfoScreen(
    navController: NavHostController,
    viewModel: UserInfoViewModel,
) {
    val userInfo by viewModel.userInfo.collectAsState()

    LaunchedEffect(Unit) { viewModel.myUserInfo() }

    Scaffold(
        topBar = {
            UserInfoAppBar(navController = navController)
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
            userInfo?.let { info ->
                Column {
                    ProfileCard(navController = navController, userInfo = info)
                    ProfileSwapCard(
                        userSwapStats = info.swapStats,
                    )
                    LazyColumn {
                        item {
                            ProfileItem(
                                text = stringResource(R.string.user_post_product),
                                count = info.swapStats.totalGoodsCount,
                            )
                            HorizontalDivider()
                            ProfileItem(
                                text = stringResource(R.string.user_swap_review),
                                count = info.reviews.size.toLong(),
                            )
                            HorizontalDivider()
                        }
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
        viewModel = UserInfoViewModel(repository = UserRepository.instance(LocalContext.current)),
    )
}
