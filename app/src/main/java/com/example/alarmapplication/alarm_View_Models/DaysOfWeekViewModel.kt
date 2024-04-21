package com.example.alarmapplication.alarm_View_Models

import androidx.lifecycle.ViewModel

class DaysOfWeekViewModel : ViewModel() {
    private var chosenDays: ArrayList<String> = arrayListOf()
    private var alarm: ArrayList<ArrayList<String>> = arrayListOf()

    private var countOfAlarm: Int = 0

    fun setDays(list: ArrayList<String>) {
        val tempArray: ArrayList<String> = arrayListOf()
        if (alarm.getOrNull(countOfAlarm) == null){
            alarm.add(tempArray)
            alarm[countOfAlarm] = list
        }
        else{
            alarm[countOfAlarm] = list
        }

    }

    fun getDays(index: Int): ArrayList<String> {
        alarm[index].removeAll { it == " " }
        return alarm[index]
    }

    fun setCountOfAlarms() {
        countOfAlarm++
    }

    fun getCountOfAlarms(): Int {
        return countOfAlarm
    }

}