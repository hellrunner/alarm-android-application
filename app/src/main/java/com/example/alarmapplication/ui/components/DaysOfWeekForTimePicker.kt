package com.example.alarmapplication.ui.components

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DayOfWeekTimePickerDialog(
    context: Context,
    initialHour: Int,
    initialMinute: Int,
    initialSelectedDays: Set<Int>,
    onConfirm: (Int, Int, Set<Int>) -> Unit,
    onCancel: () -> Unit
) {
    val selectedDays = remember { mutableStateOf(initialSelectedDays.toMutableSet()) }
    var hour by remember { mutableStateOf(initialHour) }
    var minute by remember { mutableStateOf(initialMinute) }

    // TimePickerDialog с начальным временем
    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, h: Int, m: Int ->
            hour = h
            minute = m
        },
        hour, minute, true
    )

    // Отображение дней недели
    val daysOfWeek = listOf(
        Calendar.MONDAY to "Пн",
        Calendar.TUESDAY to "Вт",
        Calendar.WEDNESDAY to "Ср",
        Calendar.THURSDAY to "Чт",
        Calendar.FRIDAY to "Пт",
        Calendar.SATURDAY to "Сб",
        Calendar.SUNDAY to "Вс"
    )

    // Диалог для выбора дней
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = {
                timePickerDialog.show()
                onConfirm(hour, minute, selectedDays.value)
            }) { Text("ОК") }
        },
        dismissButton = { TextButton(onClick = onCancel) { Text("Отмена") } },
        title = { Text("Выберите дни недели", textAlign = TextAlign.Center) },
        text = {
            Column {
                daysOfWeek.forEach { (day, label) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = selectedDays.value.contains(day),
                                onValueChange = {
                                    if (it) selectedDays.value.add(day)
                                    else selectedDays.value.remove(day)
                                }
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(label)
                        Checkbox(
                            checked = selectedDays.value.contains(day),
                            onCheckedChange = null
                        )
                    }
                }
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}


