package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.presentation.item.DayItem

data class DateBoxUIState(
    val isLoading: Boolean,
    val selectedDate: DayItem,
)
