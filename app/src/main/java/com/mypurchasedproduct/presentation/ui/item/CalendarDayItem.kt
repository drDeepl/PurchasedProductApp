package com.mypurchasedproduct.presentation.ui.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.mypurchasedproduct.presentation.ui.theme.AcidRedColor

@Composable
fun CalendarDayItem(day:CalendarDay, onClickDay: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            modifier = Modifier.size(48.dp),
            onClick= {},
            colors =  ButtonDefaults.textButtonColors(
                contentColor = AcidRedColor
            ),
        ){
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}