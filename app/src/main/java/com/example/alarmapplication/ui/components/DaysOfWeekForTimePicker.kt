package com.example.alarmapplication.ui.components

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.alarmapplication.R
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
    val dayNames = stringArrayResource(id = R.array.day_names)

    // Диалог для выбора дней
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = {
                timePickerDialog.show()
                onConfirm(hour, minute, selectedDays.value)
            }) { Text(stringResource(id = R.string.ok)) }
        },
        dismissButton = { TextButton(onClick = onCancel) { Text(stringResource(id = R.string.cancel)) } },
        title = { Text(stringResource(id = R.string.choose_days), textAlign = TextAlign.Center) },
        text = {
            Column {
                dayNames.forEachIndexed { index, label ->
                    val day = index + 1
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