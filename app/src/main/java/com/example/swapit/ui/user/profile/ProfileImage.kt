package com.example.swapit.ui.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.swapit.R
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.White

@Composable
fun ProfileImage(
    imageUrl: String,
    onImageChange: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "프로필 이미지",
            modifier =
                Modifier
                    .size(86.dp)
                    .clip(CircleShape),
        )
        Box(
            modifier =
                Modifier
                    .size(86.dp)
                    .background(Black.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                onClick = onImageChange,
                modifier = Modifier.matchParentSize(),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = "프로필 이미지 수정",
                    tint = White,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImagePreview() {
    ProfileImage(
        imageUrl = "",
        onImageChange = {},
    )
}
