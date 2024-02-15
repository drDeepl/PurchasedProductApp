package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.presentation.item.DayItem
import com.mypurchasedproduct.presentation.state.DateBoxUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Instant


import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class DateListViewModel @Inject constructor() : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private val now: DateTime = Instant.now().toDateTime()


    private val _state = MutableStateFlow(DateBoxUIState(false, DayItem(now.dayOfWeek().asShortText,now.dayOfMonth,now.monthOfYear, now.year)))
    val state = _state.asStateFlow()


    init{
        viewModelScope.launch{
            Log.wtf(TAG, "INIT")
        }

        Log.wtf(TAG, "INIT")

//        val datetime: DateTime =
//
//        Log.i(TAG, "Day of week:${datetime.dayOfWeek().asShortText}\n" +
//                "Day of month${datetime.dayOfMonth().get()}\n" +
//                "Month name + ${datetime.dayOfMonth()}\n" +
//                "Month number:${datetime.monthOfYear().get()}")


    }




}