package com.example.swapit.ui.user.profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.swapit.ui.component.DefaultTextField
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Typography

@Composable
fun ProfileNameTextField(
    label: String,
    name: String,
    placeHolder: String,
    onNameChange: (String) -> Unit,
) {
    Text(
        text = label,
        style = Typography.titleSmall,
    )
    Spacer(modifier = Modifier.padding(Paddings.medium))
    DefaultTextField(
        value = name,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = placeHolder) },
        onValueChange = { onNameChange(it) },
    )
}

@Preview
@Composable
fun ProfileNameTextFieldPreview() {
    ProfileNameTextField(
        label = "닉네임",
        name = "",
        placeHolder = "하울의움직이는성",
        onNameChange = {},
    )
}
