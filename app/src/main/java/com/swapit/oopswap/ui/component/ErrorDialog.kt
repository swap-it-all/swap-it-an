package com.swapit.oopswap.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.swapit.oopswap.data.datasource.remote.dto.response.ErrorResponse

/**
 * 에러가 발생했을 때 띄워줄 다이얼로그
 *
 * @param error 서버에서 내려준 에러 정보
 * @param onDismiss 다이얼로그 닫힐 때 호출될 콜백
 */
@Composable
fun ErrorDialog(
    error: ErrorResponse,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = error.errorCode,
                style = MaterialTheme.typography.titleSmall,
            )
        },
        text = { Text(text = error.message) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("확인")
            }
        },
    )
}
