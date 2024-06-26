package com.example.alarmapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.alarmapplication.R
import com.example.alarmapplication.model.Alarm

@Composable
fun AlarmSettingsScreen(
    alarm: Alarm,
    onUpdateAlarm: (Alarm) -> Unit,
    onDeleteAlarm: (Alarm) -> Unit,
    onCancel: () -> Unit
) {
    var time by remember { mutableStateOf(alarm.time) }
    var days by remember { mutableStateOf(alarm.days) }

    // UI с TimePicker и чекбоксами для дней недели
    Column {
        TextField(
            value = time,
            onValueChange = { time = it },
            label = { Text(stringResource(id = R.string.time)) }
        )

        // Здесь используйте UI для выбора дней недели
        // days = выберите дни

        Row {
            Button(onClick = {
                val updatedAlarm = alarm.copy(time = time, days = days)
                onUpdateAlarm(updatedAlarm)
            }) {
                Text(stringResource(id = R.string.update))
            }
            Button(onClick = { onDeleteAlarm(alarm) }) {
                Text(stringResource(id = R.string.delete))
            }
            Button(onClick = onCancel) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    }
}