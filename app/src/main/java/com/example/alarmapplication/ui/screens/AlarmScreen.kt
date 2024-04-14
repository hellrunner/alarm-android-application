package com.example.alarmapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmapplication.model.Alarm
import com.example.alarmapplication.ui.components.AlarmItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AlarmScreen() {
    var showTimePicker by remember { mutableStateOf(false) }
    val state = rememberTimePickerState()
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    var cal: Calendar = Calendar.getInstance()
    val alarms = listOf(
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),

        )

    LazyColumn(
        verticalArrangement = Arrangement.Center,
    ) {
        items(alarms) { alarm ->
            AlarmItem(alarm = alarm)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        Alignment.BottomCenter
    ) {
        FloatingActionButton(
            onClick = { showTimePicker = true },
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 1.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add by")
        }
    }

    if (showTimePicker){
        TimePickerDialog(
            onCancel = { showTimePicker = false },
            onConfirm = {
                cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, state.hour)
                cal.set(Calendar.MINUTE, state.minute)
                cal.isLenient = false
                snackScope.launch {
                    snackState.showSnackbar("Entered time: ${formatter.format(cal.time)}")
                }
                showTimePicker = false
            }) {
            TimePicker(state = state)
        }
    }

    //TODO Сделать вторую часть бужильника: реализвать запоминание выбора времени и активацию будильника

}