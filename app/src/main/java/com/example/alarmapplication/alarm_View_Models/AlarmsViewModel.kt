package com.example.alarmapplication.alarm_View_Models

import androidx.lifecycle.ViewModel
import com.example.alarmapplication.model.Alarm
import kotlin.collections.ArrayList

class AlarmsViewModel : ViewModel() {
    private val alarms: ArrayList<Alarm> = arrayListOf()
    var flag: Boolean = true
    var flagOnAlarmScreen: Boolean = true
    var indexOfAlarm = 0

    fun setAlarms(alarm: Alarm) {
        alarms.add(alarm)
    }

    fun getExistAlarms(): ArrayList<Alarm> {
        return alarms
    }

    fun plusToIndexOfAlarm() {
        indexOfAlarm++
    }
}