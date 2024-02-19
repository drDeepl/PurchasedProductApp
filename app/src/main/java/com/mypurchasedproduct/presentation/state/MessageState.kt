package com.mypurchasedproduct.presentation.state

data class MessageState(
    val isSuccess: Boolean=false,
    val isError: Boolean=false,
    val header: String="",
    val description: String="",
    val onConfirm: () -> Unit= {},
    val onDismiss: () -> Unit = {},
)