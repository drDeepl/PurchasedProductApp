package com.mypurchasedproduct.presentation.ui.item

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.mypurchasedproduct.presentation.ui.theme.AcidRedColor
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarDayItem(
    day:CalendarDay,
    selectedDay: CalendarDay,
    onClickDay: (CalendarDay) -> Unit = {}
) {
    // TODO: HIGHLIGHT SELECTED DAY

    val isDayToday = remember {mutableStateOf(day.date.equals(LocalDate.now()))}
    Box(
        modifier = Modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            modifier = Modifier.size(48.dp),
            onClick= {
                onClickDay(day)
            },
            colors =  ButtonDefaults.textButtonColors(
                contentColor = AcidRedColor,
                containerColor = if(day.date.equals(selectedDay.date)) AcidRedColor.copy(alpha=0.25f) else Color.Transparent

            ),
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray,
                    textAlign = TextAlign.Center
                )
                if(isDayToday.value) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Filled.Circle),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}