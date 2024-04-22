package com.example.alarmapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmapplication.alarm_View_Models.DaysOfWeekViewModel
import com.example.alarmapplication.alarm_View_Models.SwitchViewModel

@Composable
fun DaysOfWeek() {
    val chosenDays: ArrayList<String> = arrayListOf(" ", " ", " ", " ", " ", " ", " ")
    val week: List<String> = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    for (i in 0..6) {
        Day(i, week, listOfCheckBoxesStates(i), chosenDays)
    }
}

@Composable
fun Day(
    index: Int,
    days: List<String>,
    checkedStateFromList: MutableState<Boolean> = remember { mutableStateOf(true) },
    chosenDays: ArrayList<String>,
    daysOfWeekViewModal: DaysOfWeekViewModel = viewModel(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = days[index])
        Checkbox(
            checked = checkedStateFromList.value,
            onCheckedChange = {
                if (!checkedStateFromList.value) {
                    chosenDays[index] = days[index]
                    checkedStateFromList.value = it
                    daysOfWeekViewModal.setDays(chosenDays)
                } else if (checkedStateFromList.value) {
                    chosenDays[index] = " "
                    checkedStateFromList.value = it
                    daysOfWeekViewModal.setDays(chosenDays)
                }
            }
        )
    }
}

@Composable
fun listOfCheckBoxesStates(i: Int): MutableState<Boolean> {
    val san = remember { mutableStateOf(false) }
    val mon = remember { mutableStateOf(false) }
    val tue = remember { mutableStateOf(false) }
    val wend = remember { mutableStateOf(false) }
    val th = remember { mutableStateOf(false) }
    val fr = remember { mutableStateOf(false) }
    val sat = remember { mutableStateOf(false) }
    val listOfWeek = listOf(san, mon, tue, wend, th, fr, sat)

    return listOfWeek[i]
}