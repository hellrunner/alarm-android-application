package com.example.alarmapplication.ui.screens

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alarmapplication.AlarmActivity
import com.example.alarmapplication.MainActivity
import com.example.alarmapplication.alarm_View_Models.AlarmsViewModel
import com.example.alarmapplication.alarm_View_Models.DaysOfWeekViewModel
import com.example.alarmapplication.model.Alarm
import com.example.alarmapplication.navigation.Screens
import com.example.alarmapplication.ui.components.AlarmItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    navControllerFromAppNavigation: NavHostController = rememberNavController(),
    alarmsViewModel: AlarmsViewModel,
    daysOfWeekViewModal: DaysOfWeekViewModel = viewModel(),
) {


    var showTimePicker by remember { mutableStateOf(false) } //Показать тайм пикер

    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val state = rememberTimePickerState()
    val snackState = remember { SnackbarHostState() }

    val snackScope = rememberCoroutineScope()
    val context = LocalContext.current

    var cal: Calendar

    val alarms = remember { MutableStateFlow(listOf<Alarm>()) }
    val noteList by remember { alarms }.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.Center,
    ) {
        if (alarmsViewModel.getExistAlarms().isNotEmpty() and alarmsViewModel.flagOnAlarmScreen) {
            val initializerArrayList: ArrayList<Alarm> = alarmsViewModel.getExistAlarms()
            val bufferArray: ArrayList<Alarm> = initializerArrayList
            alarms.value = bufferArray
        }
        items(alarms.value) { alarm ->
            AlarmItem(alarm = alarm) //Создаём карточку будильника.
            // Тут много раз вызывается функция AlarmItem
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

    if (showTimePicker) {
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
                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    /*alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        cal.timeInMillis,
                        AlarmManager.INTERVAL_DAY,
                        getAlarmActionPending(context)
                    )*/

                    val alarmClockInfo: AlarmManager.AlarmClockInfo = AlarmManager.AlarmClockInfo(
                        cal.timeInMillis,
                        getAlarmPendingIntent(context)
                    )
                    alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPending(context))

                    val tempNewList = ArrayList(noteList)

                    val chosenDay =
                        if (daysOfWeekViewModal.getDays(daysOfWeekViewModal.getCountOfAlarms())[0] != "Not value")
                        {
                            getChosenDays(daysOfWeekViewModal.getDays(daysOfWeekViewModal.getCountOfAlarms()))
                        } else {
                            "Ежедневно"
                        }

                    val alarm =
                        Alarm(
                            simpleDateFormat.format(cal.time),
                            chosenDay,
                            stateOnOff = true,//При создании будильника он будет включённым
                            existAlarm = false, //Его пока ещё нет
                            index = alarmsViewModel.indexOfAlarm
                        )

                    tempNewList.add(alarm)
                    alarms.value = tempNewList

                    daysOfWeekViewModal.setCountOfAlarms()
                    alarmsViewModel.setAlarms(alarm)
                    alarmsViewModel.plusToIndexOfAlarm()
                }
                showTimePicker = false
                makeToast(context, simpleDateFormat.format(cal.time))

            })
        {
            TimePicker(state = state)
        }

    }

    navControllerFromAppNavigation.addOnDestinationChangedListener { _, destination, _ ->
        if ((destination.route == Screens.MusicScreen.name) or (destination.route == Screens.ArticlesScreen.name)) {
            if (alarmsViewModel.flagOnAlarmScreen) {
                alarmsViewModel.flagOnAlarmScreen = false
            }
        } else if (destination.route == Screens.AlarmScreen.name) {
            alarmsViewModel.flagOnAlarmScreen = true
        }

    }

}

private fun getAlarmPendingIntent(context: Context): PendingIntent {
    val intent = Intent(context, MainActivity::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    return PendingIntent.getActivity(
        context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

private fun getAlarmActionPending(context: Context): PendingIntent {
    val intent = Intent(context, AlarmActivity::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    return PendingIntent.getActivity(
        context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

@RequiresApi(Build.VERSION_CODES.S)
fun canScheduleExactAlarms(context: Context): Boolean {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    return alarmManager?.canScheduleExactAlarms() ?: false
}

fun makeToast(context: Context, time: String) {
    Toast.makeText(
        context,
        "Будильник установлен на $time",
        Toast.LENGTH_SHORT
    ).show()
}

fun getChosenDays(numOfArray: ArrayList<String>): String {
    if (numOfArray.isEmpty()){
        return "No days"
    }
    var days = ""
    for (i in 0..<numOfArray.size) {
        days +=
            if (i == numOfArray.size - 1) {
                numOfArray[i]
            } else {
                numOfArray[i] + " "
            }
    }
    return days
}