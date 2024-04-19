package com.example.alarmapplication.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmapplication.alarm_View_Models.DaysOfWeekViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun DaysOfWeek(
    days: List<String>
){
    val listMain: ArrayList<String> = arrayListOf(" ", " ", " ", " ", " ", " ", " ")

    for (i in 0..6){
        Day(i, days, listOfDaysStates(i), listMain)
    }
}

@Composable
fun Day(
    index: Int,
    days: List<String>,
    checkedStateFromList: MutableState<Boolean> = remember { mutableStateOf(true)},
    chosenDays: ArrayList<String>,
    daysOfWeekViewModal: DaysOfWeekViewModel = viewModel()
){
    val context: Context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = days[index])
        Checkbox(
            checked = checkedStateFromList.value,
            onCheckedChange = {
                if (!checkedStateFromList.value) {
                    chosenDays.add(index, days[index])
                    checkedStateFromList.value = it
                    daysOfWeekViewModal.setDays(chosenDays)
                }
                else if(checkedStateFromList.value){
                    chosenDays.removeAt(index)
                    chosenDays.add(index, " ")
                    checkedStateFromList.value = it
                    daysOfWeekViewModal.setDays(chosenDays)
                }
                //TODO разобраться с обращением к каждому элементу строки

            }
        )
    }
}

@Composable
fun listOfDaysStates(i: Int): MutableState<Boolean> {
    val san = remember { mutableStateOf(false)}
    val mon = remember { mutableStateOf(false)}
    val tue = remember { mutableStateOf(false)}
    val wend = remember { mutableStateOf(false)}
    val th = remember { mutableStateOf(false)}
    val fr = remember { mutableStateOf(false)}
    val sat = remember { mutableStateOf(false)}
    val listOfWeek = listOf(san, mon, tue, wend, th, fr,sat)

    return listOfWeek[i]
}