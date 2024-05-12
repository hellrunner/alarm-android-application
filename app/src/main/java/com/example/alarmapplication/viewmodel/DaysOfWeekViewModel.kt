package com.example.alarmapplication.viewmodel

import androidx.lifecycle.ViewModel

class DaysOfWeekViewModel : ViewModel() {
    private var alarm: ArrayList<ArrayList<String>> = arrayListOf()
    private var countOfAlarm: Int = 0

    fun setDays(list: ArrayList<String>) {
        val tempArray: ArrayList<String> = arrayListOf()
        if (alarm.getOrNull(countOfAlarm) == null) {
            alarm.add(tempArray)
            alarm[countOfAlarm] = list
        } else {
            alarm[countOfAlarm] = list
        }
    }

    fun getDays(index: Int): ArrayList<String> {
        if (alarm.getOrNull(index) != null){
            alarm[index].removeAll { it == " " }
        }
        else {
            val tempArray: ArrayList<String> = arrayListOf("Not value")
            val noAlarm: ArrayList<ArrayList<String>> = arrayListOf(tempArray)
            return noAlarm[0]
        }
        return alarm[index]
    }

    fun setCountOfAlarms() {
        countOfAlarm++
    }

    fun getCountOfAlarms(): Int {
        return countOfAlarm
    }


}