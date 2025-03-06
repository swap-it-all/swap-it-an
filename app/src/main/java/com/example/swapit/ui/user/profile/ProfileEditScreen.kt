package com.example.swapit.ui.user.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.swapit.domain.repository.UserRepository
import com.example.swapit.ui.component.DefaultButton
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.user.UserInfoViewModel

@Composable
fun ProfileEditScreen(
    navController: NavHostController,
    viewModel: UserInfoViewModel,
) {
    val userInfo by viewModel.userInfo.collectAsState()
    val saveCompleted by viewModel.saveCompleted.collectAsState()

    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                viewModel.updateProfileImage(uri)
            }
        },
    )

    LaunchedEffect(saveCompleted) {
        if (saveCompleted) {
            viewModel.resetSaveCompleted()
            navController.popBackStack()
        }
    }

    userInfo?.let { info ->
        Scaffold(
            topBar = {
                ProfileEditAppBar(navController)
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
                    modifier =
                    Modifier
                        .padding(Paddings.xlarge)
                        .fillMaxSize(),
                ) {
                    ProfileImage(imageUrl = info.profileImageUrl,
                        onImageChange = {
                            photoLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                    ProfileNameTextField(
                        label = "닉네임",
                        name = info.nickname,
                        placeHolder = info.nickname,
                        onNameChange = viewModel::updateNickname,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DefaultButton(
                        text = "수정하기",
                        enabled = true,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        viewModel.saveUserInfo()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileEditScreenPreview() {
    ProfileEditScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = UserInfoViewModel(repository = UserRepository.instance(LocalContext.current)),
    )
}
