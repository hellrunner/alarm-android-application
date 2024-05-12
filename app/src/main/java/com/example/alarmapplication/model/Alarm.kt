package com.example.alarmapplication.model

data class Alarm(
    val time: String,
    var days: String,
    var stateOnOff: Boolean,
    var existAlarm: Boolean,
    val index: Int
)
