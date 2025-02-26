package com.example.swapit.ui.chat.room

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.swapit.ui.chat._chatCardData
import com.example.swapit.ui.component.BackButton
import com.example.swapit.ui.component.MenuButton
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomAppBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = { BackButton(modifier = Modifier.padding(start = Paddings.xlarge), navController = navController) },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = _chatCardData.userName,
                    style = Typography.bodyMedium,
                    color = Black,
                )
            }
        },
        actions = {
            MenuButton(navController = navController, color = Black)
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

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    ChatRoomAppBar(NavHostController(LocalContext.current))
}
