package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.mypurchasedproduct.presentation.state.CalendarViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(): ViewModel(){

    private val TAG: String = this.javaClass.simpleName


    private val _state = MutableStateFlow(CalendarViewState(
        isLoading = false,
        currentMonth = YearMonth.now(),
        currentDay = CalendarDay(LocalDate.now(), DayPosition.InDate)
    ))

    val state = _state.asStateFlow()


    fun setCurrentMonth(yearMonth: YearMonth){
        viewModelScope.launch {
            Log.d(TAG, "SET CURRENT MONTH")
            _state.update { state ->
                state.copy(
                    currentMonth = yearMonth,
                )
            }
        }
    }

    fun setCurrentDay(day: CalendarDay){
        viewModelScope.launch {
            Log.d(TAG, "SET CURRENT DAY")
            _state.update { state ->
                state.copy(
                    currentDay = day
                )
            }
        }
    }

    fun setLoading(isLoading: Boolean){
        Log.d(TAG, "SET LOADING")
        _state.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }
}