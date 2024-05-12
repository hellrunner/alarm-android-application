package com.example.alarmapplication.ui.screens

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alarmapplication.viewmodel.AlarmsViewModel
import com.example.alarmapplication.model.Alarm
import com.example.alarmapplication.ui.components.AlarmItem
import java.util.*

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AlarmScreen(
    navController: NavHostController,
    alarmsViewModel: AlarmsViewModel
) {
    val context = LocalContext.current
    val alarms by alarmsViewModel.alarms.collectAsState()
    var showDaysDialog by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var editingAlarm by remember { mutableStateOf<Alarm?>(null) }
    var selectedDays = remember { mutableSetOf<Int>() }
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    LazyColumn(verticalArrangement = Arrangement.Top) {
        items(alarms.sortedByDescending { it.index }) { alarm ->
            AlarmItem(
                alarm = alarm,
                onEdit = {
                    editingAlarm = alarm
                    selectedDays.clear()
                    selectedDays.addAll(getDaysSetFromString(alarm.days))
                    selectedHour = alarm.time.substringBefore(":").toInt()
                    selectedMinute = alarm.time.substringAfter(":").toInt()
                    showDaysDialog = true
                },
                onRemove = {
                    alarmsViewModel.removeAlarm(context, alarm)
                },
                onToggle = { isEnabled ->
                    alarmsViewModel.toggleAlarm(context, alarm, isEnabled)
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        FloatingActionButton(
            onClick = {
                editingAlarm = null
                selectedDays.clear()
                selectedHour = 0
                selectedMinute = 0
                showDaysDialog = true
            },
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 1.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить будильник")
        }
    }

    // Диалог выбора дней недели
    if (showDaysDialog) {
        val daysOfWeek = listOf(
            Calendar.MONDAY to "Пн",
            Calendar.TUESDAY to "Вт",
            Calendar.WEDNESDAY to "Ср",
            Calendar.THURSDAY to "Чт",
            Calendar.FRIDAY to "Пт",
            Calendar.SATURDAY to "Сб",
            Calendar.SUNDAY to "Вс"
        )

        AlertDialog(
            onDismissRequest = { showDaysDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDaysDialog = false
                    showTimePicker = true
                }) { Text("Следующий шаг") }
            },
            dismissButton = { TextButton(onClick = { showDaysDialog = false }) { Text("Отмена") } },
            title = { Text("Выберите дни недели", textAlign = TextAlign.Center) },
            text = {
                Column {
                    daysOfWeek.forEach { (day, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = selectedDays.contains(day),
                                    onValueChange = {
                                        if (it) selectedDays.add(day)
                                        else selectedDays.remove(day)
                                    }
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(label)
                            Checkbox(
                                checked = selectedDays.contains(day),
                                onCheckedChange = null
                            )
                        }
                    }
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

    // Диалог выбора времени
    if (showTimePicker) {
        val timePickerDialog = TimePickerDialog(
            context,
            { _: TimePicker, hour: Int, minute: Int ->
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    if (before(Calendar.getInstance())) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }

                val formattedTime = String.format("%02d:%02d", hour, minute)
                val daysString = selectedDays.joinToString(", ") { day ->
                    when (day) {
                        Calendar.MONDAY -> "Пн"
                        Calendar.TUESDAY -> "Вт"
                        Calendar.WEDNESDAY -> "Ср"
                        Calendar.THURSDAY -> "Чт"
                        Calendar.FRIDAY -> "Пт"
                        Calendar.SATURDAY -> "Сб"
                        Calendar.SUNDAY -> "Вс"
                        else -> ""
                    }
                }

                val newAlarm = editingAlarm?.copy(time = formattedTime, days = daysString)
                    ?: Alarm(
                        time = formattedTime,
                        days = daysString,
                        stateOnOff = true,
                        existAlarm = true,
                        index = alarmsViewModel.getNextAlarmIndex()
                    )

                if (editingAlarm == null) {
                    alarmsViewModel.addAlarm(newAlarm)
                } else {
                    alarmsViewModel.editAlarm(newAlarm)
                }

                if (newAlarm.stateOnOff) {
                    alarmsViewModel.setAlarm(context, newAlarm)
                }

                showTimePicker = false
            },
            selectedHour, selectedMinute, true
        )
        timePickerDialog.show()
    }
}

fun getDaysSetFromString(daysString: String): Set<Int> {
    return daysString.split(", ").mapNotNull {
        when (it) {
            "Пн" -> Calendar.MONDAY
            "Вт" -> Calendar.TUESDAY
            "Ср" -> Calendar.WEDNESDAY
            "Чт" -> Calendar.THURSDAY
            "Пт" -> Calendar.FRIDAY
            "Сб" -> Calendar.SATURDAY
            "Вс" -> Calendar.SUNDAY
            else -> null
        }
    }.toSet()
}
