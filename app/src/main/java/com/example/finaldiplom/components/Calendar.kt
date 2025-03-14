package com.example.finaldiplom.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finaldiplom.enums.Screens
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream


@Composable
fun CalendarPreview(
    navController: NavController, onDateSelected: (CalendarData.Date) -> Unit
) {
    Calendar(navController, Modifier.padding(16.dp), onDateSelected)
}


@Composable
fun Calendar(
    navController: NavController, modifier: Modifier, onDateSelected: (CalendarData.Date) -> Unit
) {
    val data = DataSource()
    var calendarUiModel by remember { mutableStateOf(data.getData(lastSelectedDate = data.today)) }

    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        CalendarHeader(data = calendarUiModel, onPrevClickListener = { startDate ->
            val finalStartDate = startDate.minusDays(1)
            calendarUiModel = data.getData(
                startDate = finalStartDate, lastSelectedDate = calendarUiModel.selectedDate.date
            )
        }, onNextClickListener = { endDate ->
            val finalStartDate = endDate.plusDays(2)
            calendarUiModel = data.getData(
                startDate = finalStartDate, lastSelectedDate = calendarUiModel.selectedDate.date
            )
        }, navController = navController // Передаем navController
        )

        CalendarContent(data = calendarUiModel, onDateClickListener = { date ->
            calendarUiModel = calendarUiModel.copy(selectedDate = date,
                visibleDates = calendarUiModel.visibleDates.map {
                    it.copy(
                        isSelected = it.date.isEqual(date.date)
                    )
                })
            onDateSelected(date) // Вызываем onDateSelected при клике
        }, navController)
    }
}


@Composable
fun CalendarHeader(
    data: CalendarData,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
    navController: NavController
) {

    Row(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 25.dp)
    ) {
        Text(
            text = if (data.selectedDate.isToday) {
                "Today"
            } else {
                data.selectedDate.date.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                )
            }, modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )

        IconButton(onClick = { onPrevClickListener(data.startDate.date) }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
            )
        }

        IconButton(onClick = { onNextClickListener(data.endDate.date) }) {
            Icon(
                imageVector = Icons.Filled.ArrowForward, contentDescription = "Next"
            )
        }

        IconButton(onClick = {
            val selectedDate = data.selectedDate.date
            navController.navigate(Screens.addSpending.name + "/${selectedDate}")
        }) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "Add"
            )
        }
    }
}

@Composable
fun Cards(
    date: CalendarData.Date,
    onDateSelected: (CalendarData.Date) -> Unit,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable {
                onDateSelected(date)
            },
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Text(
                text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CalendarContent(
    data: CalendarData,
    onDateClickListener: (CalendarData.Date) -> Unit,
    navController: NavController
) {
    LazyRow(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
        items(items = data.visibleDates) { date ->
            Cards(
                date = date, onDateClickListener, navController
            )
        }
    }
}


data class CalendarData(val selectedDate: Date, val visibleDates: List<Date>) {
    val startDate: Date = visibleDates.first()
    val endDate: Date = visibleDates.last()

    data class Date(
        val date: LocalDate, val isSelected: Boolean, val isToday: Boolean
    ) {
        val day: String = date.format(DateTimeFormatter.ofPattern("E"))
    }
}

class DataSource {
    val currentDate = LocalDate.now()
    val year = currentDate.year.toString()
    val month = currentDate.month.toString()

    val today: LocalDate
        get() {
            return LocalDate.now()
        }

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarData {
        val firstWeekDay = startDate.with(DayOfWeek.MONDAY)
        val weekEnd = firstWeekDay.plusDays(7)
        val visibleDates = getDatesBetween(firstWeekDay, weekEnd)

        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val daysCount = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(1)
        }.limit(daysCount).collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>, lastSelectedDate: LocalDate
    ): CalendarData {
        return CalendarData(selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            })
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarData.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}