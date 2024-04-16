package com.example.alarmapplication.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class Alarm(
    val time: String,
    val days: String,
    val state: Boolean
)
