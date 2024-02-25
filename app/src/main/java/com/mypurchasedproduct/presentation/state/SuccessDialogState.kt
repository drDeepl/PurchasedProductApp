package com.mypurchasedproduct.presentation.state

data class SuccessDialogState(
    val isActive: Boolean = false,
    val header: String = "",
    val onConfirm: () -> Unit = {},
)
