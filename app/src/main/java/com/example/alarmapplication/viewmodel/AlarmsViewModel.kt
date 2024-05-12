package com.example.alarmapplication.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.alarmapplication.AlarmActivity
import com.example.alarmapplication.model.Alarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

class AlarmsViewModel : ViewModel() {
    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> = _alarms

    var flagOnAlarmScreen = true
    var indexOfAlarm = 0
    private lateinit var sharedPreferences: SharedPreferences

    fun initAlarmsArray(sharedP: SharedPreferences) {
        sharedPreferences = sharedP
        val mainKey: String? = sharedPreferences.getString("mainKey", "Not Save")

        if (mainKey != "Not Save") {
            val arrayOfKeys = mainKey?.split(" ")?.filter { it.isNotEmpty() }

            if (arrayOfKeys != null) {
                val loadedAlarms = mutableListOf<Alarm>()
                for (key in arrayOfKeys) {
                    val tempStr = sharedPreferences
                        .getString(key, "No value")
                        ?.split(" ")?.filter { it.isNotEmpty() }

                    if (tempStr?.getOrNull(0) != "No value") {
                        val alarm = Alarm(
                            tempStr?.getOrNull(0) ?: "",
                            tempStr?.getOrNull(1)?.replace(",", " ") ?: "",
                            tempStr?.getOrNull(2)?.toBoolean() ?: true,
                            tempStr?.getOrNull(3)?.toBoolean() ?: true,
                            tempStr?.getOrNull(4)?.toInt() ?: 0
                        )
                        loadedAlarms.add(alarm)
                    }
                }
                _alarms.value = loadedAlarms
                indexOfAlarm = loadedAlarms.maxOfOrNull { it.index }?.plus(1) ?: 0
            }
        }
    }

    fun setAlarm(context: Context, alarm: Alarm) {
        if (!alarm.stateOnOff) return

        // Подготовка намерения для будильника
        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            alarm.index,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmTimeInMillis = convertTimeToMillis(alarm.time)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTimeInMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            Log.e("AlarmError", "SecurityException: ${e.message}")
        }
    }



    fun addAlarm(alarm: Alarm) {
        _alarms.value = listOf(alarm) + _alarms.value // Добавить новые будильники в начало списка
        indexOfAlarm++
        saveAlarmsToPreferences()
    }

    fun removeAlarm(context: Context, alarm: Alarm) {
        // Отменяем будильник перед удалением
        cancelAlarm(context, alarm)

        _alarms.value = _alarms.value.filter { it.index != alarm.index }
        saveAlarmsToPreferences()
    }



    fun editAlarm(updatedAlarm: Alarm) {
        _alarms.value =
            _alarms.value.map { if (it.index == updatedAlarm.index) updatedAlarm else it }
        saveAlarmsToPreferences()
    }

    private fun saveAlarmsToPreferences() {
        val editor = sharedPreferences.edit()
        val keys = _alarms.value.joinToString(" ") { "alarm_${it.index}" }

        editor.putString("mainKey", keys)
        for (alarm in _alarms.value) {
            val key = "alarm_${alarm.index}"
            val value = "${alarm.time} ${
                alarm.days.replace(
                    " ",
                    ","
                )
            } ${alarm.stateOnOff} ${alarm.existAlarm} ${alarm.index}"
            editor.putString(key, value)
        }
        editor.apply()
    }

    fun getNextAlarmIndex(): Int = indexOfAlarm

    fun toggleAlarm(context: Context, alarm: Alarm, isEnabled: Boolean) {
        alarm.stateOnOff = isEnabled
        if (isEnabled) {
            setAlarmBasedOnDaysOfWeek(context, alarm)
        } else {
            cancelAlarm(context, alarm)
        }
        editAlarm(alarm) // Сохраняем измененное состояние
    }

    fun setAlarmBasedOnDaysOfWeek(context: Context, alarm: Alarm) {
        val days = alarm.days.split(", ").mapNotNull { dayStringToCalendarDay(it) }
        val alarmTime = alarm.time.split(":").map { it.toInt() }
        val hour = alarmTime[0]
        val minute = alarmTime[1]

        days.forEach { day ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                set(Calendar.DAY_OF_WEEK, day)

                // Установите будильник на следующую неделю, если время уже прошло
                if (before(Calendar.getInstance())) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }

            // Создайте новый Alarm объект с теми же данными
            val newAlarm = alarm.copy()

            // Используйте новый метод `setAlarm`, который принимает объект `Alarm`
            setAlarm(context, newAlarm)
        }
    }


    fun cancelAlarm(context: Context, alarm: Alarm) {
        // Отменить запланированный будильник
        val alarmIntent = Intent(context, AlarmActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            alarm.index,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun convertTimeToMillis(time: String): Long {
        val parts = time.split(":")
        val hour = parts[0].toIntOrNull() ?: 0
        val minute = parts[1].toIntOrNull() ?: 0

        val now = Calendar.getInstance()
        now.set(Calendar.HOUR_OF_DAY, hour)
        now.set(Calendar.MINUTE, minute)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)

        // Если время уже прошло сегодня, ставим на следующий день
        if (now.before(Calendar.getInstance())) {
            now.add(Calendar.DATE, 1)
        }

        return now.timeInMillis
    }

    fun dayStringToCalendarDay(day: String): Int? {
        return when (day) {
            "Пн" -> Calendar.MONDAY
            "Вт" -> Calendar.TUESDAY
            "Ср" -> Calendar.WEDNESDAY
            "Чт" -> Calendar.THURSDAY
            "Пт" -> Calendar.FRIDAY
            "Сб" -> Calendar.SATURDAY
            "Вс" -> Calendar.SUNDAY
            else -> null
        }
    }

}
