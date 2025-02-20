package com.example.swapit.ui.user.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.swapit.ui.component.AppBar
import com.example.swapit.ui.component.DefaultButton
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Paddings

@Composable
fun ProfileEditScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppBar(navController = navController)
        },
    ) { contentPadding ->
        Surface(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = BackgroundColor,
        ) {
            Column(
                modifier = Modifier
                    .padding(Paddings.xlarge)
                    .fillMaxSize(),
            ) {
                ProfileImage()
                ProfileNameTextField(
                    label = "닉네임",
                    name = "",
                    placeHolder = "하울의움직이는성",
                    onNameChange = {},
                )
                Spacer(modifier = Modifier.weight(1f))
                DefaultButton(text = "수정하기", enabled = true, modifier = Modifier.fillMaxWidth()) { }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileEditScreenPreview() {
    ProfileEditScreen(
        navController = NavHostController(LocalContext.current)
    )
}
