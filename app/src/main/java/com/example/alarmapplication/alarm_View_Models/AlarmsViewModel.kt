package com.example.alarmapplication.alarm_View_Models

import androidx.lifecycle.ViewModel
import com.example.alarmapplication.model.Alarm
import kotlin.collections.ArrayList

class AlarmsViewModel : ViewModel(){
    private val alarms = ArrayList<Alarm>()

    fun setAlarms(arrayList: ArrayList<Alarm>){
        alarms.addAll(arrayList)
    }

    fun getExistAlarms(): ArrayList<Alarm>{
        return alarms
    }
}