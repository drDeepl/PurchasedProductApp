package com.mypurchasedproduct.presentation.state

import com.kizitonwose.calendar.core.CalendarDay
import java.time.YearMonth
data class CalendarViewState(
    val isLoading: Boolean,
    val currentMonth: YearMonth,
    val currentDay: CalendarDay,
)
