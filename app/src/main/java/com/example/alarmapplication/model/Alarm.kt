package com.example.alarmapplication.model

data class Alarm(
    val time: String,
    val days: String,
    val stateOnOff: Boolean,
    var existAlarm: Boolean,
    val index: Int
)
