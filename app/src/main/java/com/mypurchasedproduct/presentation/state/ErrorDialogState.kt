package com.mypurchasedproduct.presentation.state

data class ErrorDialogState(
    val isActive: Boolean = false,
    val header: String = "Упс.. Что-то пошло не так",
    val errors: MutableList<String> = mutableListOf(),
    val onConfirm: () -> Unit = {},
)
