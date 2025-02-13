package com.example.swap_it.ui.post.model

data class AlertDialogState(
    val title: String = "",
    val description: String = "",
    val onClickConfirm: () -> Unit = {},
    val onClickCancel: () -> Unit = {},
)
