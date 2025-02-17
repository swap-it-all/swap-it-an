package com.example.swapit.ui.shopping.detail.select

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.swapit.R
import com.example.swapit.ui.component.AlertDialog
import com.example.swapit.ui.shopping.productCardData

@Composable
fun DialogSection(
    openDialog: Boolean,
    onClickCancel: () -> Unit,
    navController: NavHostController,
) {
    if (openDialog) {
        AlertDialog(
            description = stringResource(R.string.product_select_dialog_description),
            title = stringResource(R.string.product_select_dialog_message),
            imgUri = productCardData.imageUri,
            onClickCancel = onClickCancel,
            onClickConfirm = { navController.popBackStack() },
            cancelText = stringResource(R.string.cancel_message),
            confirmText = stringResource(R.string.product_select_swap_request_dialog_confirm_button),
        )
    }
}
