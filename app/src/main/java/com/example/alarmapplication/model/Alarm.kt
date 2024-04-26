package com.example.alarmapplication.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Класс данных, представляющий будильник.
 *
 * @property time Время срабатывания будильника, представленное в виде строки.
 * @property days Строка, представляющая дни, в которые будильник должен повторяться.
 *                Например, "Пн, Вт, Ср".
 * @property state Булево значение, указывающее, активен (true) или неактивен (false) будильник.
 */
data class Alarm(
    val time: String,       // Время срабатывания будильника в определённом формате.
    val days: String,       // Строка с перечислением дней, когда должен срабатывать будильник.
    val state: Boolean      // Булево состояние, показывающее, включён будильник или выключен.
)
