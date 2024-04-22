package com.example.alarmapplication.alarm_View_Models

import androidx.lifecycle.ViewModel

class SwitchViewModel : ViewModel() {
    private var switchesArray: ArrayList<Boolean> = arrayListOf()

    fun setSwitch(state: Boolean, index: Int){
        if(switchesArray.getOrNull(index) == null){
            switchesArray.add(false)
            switchesArray[index] = state
        } else{
            switchesArray[index] = state
        }

    }
    fun getSwitch(index: Int): Boolean{
        return switchesArray[index]
    }

    fun getSwitches(): ArrayList<Boolean>{
        return switchesArray
    }
}