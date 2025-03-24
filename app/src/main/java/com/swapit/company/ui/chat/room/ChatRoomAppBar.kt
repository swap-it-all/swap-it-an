package com.swapit.company.ui.chat.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.swapit.company.ui.chat.ChatViewModel
import com.swapit.company.ui.component.BackButton
import com.swapit.company.ui.component.MenuButton
import com.swapit.company.ui.theme.Black
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Typography
import com.swapit.company.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomAppBar(
    navController: NavHostController,
    viewModel: ChatViewModel,
) {
    TopAppBar(
        navigationIcon = { BackButton(modifier = Modifier.padding(Paddings.xlarge), navController = navController) },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    viewModel.chatRoomProduct.value.nickname,
                    style = Typography.bodyMedium,
                    color = Black,
                )
            }
        },
        actions = {
            MenuButton(navController = navController, color = Black, modifier = Modifier.padding(Paddings.xlarge))
        },
        colors =
            TopAppBarColors(
                containerColor = White,
                navigationIconContentColor = White,
                actionIconContentColor = White,
                scrolledContainerColor = White,
                titleContentColor = White,
            ),
    )
}
