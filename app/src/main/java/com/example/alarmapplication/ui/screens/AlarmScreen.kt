package com.example.alarmapplication.ui.screens

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmapplication.AlarmActivity
import com.example.alarmapplication.MainActivity
import com.example.alarmapplication.model.Alarm
import com.example.alarmapplication.ui.components.AlarmItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AlarmScreen() {
    var showTimePicker by remember { mutableStateOf(false) }
    val state = rememberTimePickerState()
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    val context = LocalContext.current
    var cal: Calendar = Calendar.getInstance()
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
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

                if (canScheduleExactAlarms(context)) {
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val alarmClockInfo: AlarmManager.AlarmClockInfo = AlarmManager.AlarmClockInfo(cal.timeInMillis, getAlarmPendingIntent(context))

                    alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPending(context))
                    Toast.makeText(context,
                        "Будильник установлен на " + simpleDateFormat.format(cal.time), Toast.LENGTH_SHORT).show()
                }

                showTimePicker = false
            }) {
            TimePicker(state = state)
        }

    }

    //TODO Сделать вторую часть бужильника: реализвать запоминание выбора времени и активацию будильника

}

private fun getAlarmPendingIntent(context: Context): PendingIntent {
    val intent = Intent(context, MainActivity::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    return PendingIntent.getActivity(context,0,intent,
        PendingIntent.FLAG_IMMUTABLE and
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
}

private fun getAlarmActionPending(context: Context): PendingIntent {
    val intent = Intent(context, AlarmActivity::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    return PendingIntent.getActivity(context,0,intent,
        PendingIntent.FLAG_IMMUTABLE and
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE )
}

@RequiresApi(Build.VERSION_CODES.S)
fun canScheduleExactAlarms(context: Context): Boolean {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    return alarmManager?.canScheduleExactAlarms() ?: false
}