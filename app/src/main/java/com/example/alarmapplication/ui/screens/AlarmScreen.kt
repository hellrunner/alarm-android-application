package com.example.alarmapplication.ui.screens

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alarmapplication.R
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
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }

    LazyColumn(verticalArrangement = Arrangement.Top) {
        items(alarms.sortedByDescending { it.index }) { alarm ->
            AlarmItem(
                alarm = alarm,
                onEdit = {
                    editingAlarm = alarm
                    selectedDays.clear()
                    selectedDays.addAll(getDaysSetFromString(context, alarm.days))
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
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_alarm)
            )
        }
    }

    // Диалог выбора дней недели
    if (showDaysDialog) {
        val daysOfWeek = stringArrayResource(id = R.array.day_names)

        AlertDialog(
            onDismissRequest = { showDaysDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDaysDialog = false
                    showTimePicker = true
                }) { Text(stringResource(R.string.next)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDaysDialog = false
                }) { Text(stringResource(R.string.cancel)) }
            },
            title = { Text(stringResource(R.string.choose_days), textAlign = TextAlign.Center) },
            text = {
                Column {
                    daysOfWeek.forEachIndexed { index, label ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = selectedDays.contains(index + 1),
                                    onValueChange = {
                                        if (it) selectedDays.add(index + 1)
                                        else selectedDays.remove(index + 1)
                                    }
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(label)
                            Checkbox(
                                checked = selectedDays.contains(index + 1),
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
                val dayNames = context.resources.getStringArray(R.array.day_names)
                val daysString = selectedDays.joinToString(", ") { dayNames[(it - 1) % 7] }

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

fun getDaysSetFromString(context: Context, daysString: String): Set<Int> {
    val dayNames = context.resources.getStringArray(R.array.day_names)
    return daysString.split(", ").mapNotNull {
        when (it) {
            dayNames[0] -> Calendar.SUNDAY
            dayNames[1] -> Calendar.MONDAY
            dayNames[2] -> Calendar.TUESDAY
            dayNames[3] -> Calendar.WEDNESDAY
            dayNames[4] -> Calendar.THURSDAY
            dayNames[5] -> Calendar.FRIDAY
            dayNames[6] -> Calendar.SATURDAY
            else -> null
        }
    }.toSet()
}