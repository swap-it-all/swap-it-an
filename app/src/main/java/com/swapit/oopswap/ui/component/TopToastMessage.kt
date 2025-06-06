package com.swapit.oopswap.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.swapit.oopswap.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun TopToastMessage(
    message: String,
    iconResId: Int,
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    durationMillis: Long = 2000,
) {
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(durationMillis)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp)
                .zIndex(1f),
    ) {
        Box(
            modifier =
                modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(vertical = 20.dp, horizontal = 16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Primary,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = message, color = Color.Black)
            }
        }
    }
}
