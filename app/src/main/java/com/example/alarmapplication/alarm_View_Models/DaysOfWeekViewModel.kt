package com.example.alarmapplication.alarm_View_Models

import androidx.lifecycle.ViewModel

class DaysOfWeekViewModel : ViewModel(){
    private var chosenDays: MutableSet<String> = mutableSetOf(" ")

    fun setDays(list: ArrayList<String>){
        list.toSet()
        chosenDays.addAll(list)
    }

    fun getDays(): ArrayList<String>{
        var _tempList: ArrayList<String> = arrayListOf()
        _tempList.addAll(chosenDays)
        _tempList.removeAll {it == " "}
        return _tempList
    }

}