package com.swapit.oopswap.ui.shopping.detail.select

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.swapit.oopswap.R
import com.swapit.oopswap.ui.component.AlertDialog

@Composable
fun DialogSection(
    openDialog: Boolean,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
    imgUri: String?,
) {
    if (openDialog) {
        AlertDialog(
            title = stringResource(R.string.product_select_dialog_message),
            imgUri = imgUri ?: "",
            onClickCancel = onClickCancel,
            onClickConfirm = onClickConfirm,
            cancelText = stringResource(R.string.cancel_message),
            confirmText = stringResource(R.string.product_select_swap_request_dialog_confirm_button),
        )
    }
}
