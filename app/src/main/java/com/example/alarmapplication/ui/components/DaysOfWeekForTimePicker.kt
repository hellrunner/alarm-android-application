package com.example.alarmapplication.ui.components

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
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun DaysOfWeek(
    days: List<String>,
){
    val daysOfWeek = remember { MutableStateFlow(listOf<String>()) }
    val noteList by remember { daysOfWeek }.collectAsState()

    var listMain: ArrayList<String> = ArrayList()

    for (i in 0..6){
        Day(i, days, listOfDaysStates(i), /*daysOfWeek, noteList*/listMain)
    }
}

@Composable
fun Day(
    index: Int,
    days: List<String>,
    checkedStateFromList: MutableState<Boolean> = remember { mutableStateOf(true)},
    chosenDays: ArrayList<String>
    //daysOfWeek: MutableStateFlow<List<String>> = remember { MutableStateFlow(ArrayList()) },
    //noteList: State<List<String>> = remember { daysOfWeek }.collectAsState(),
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = days[index])
        Checkbox(
            checked = checkedStateFromList.value,
            onCheckedChange = {
                if (checkedStateFromList.value) {
                    /*noteList.add(days[index])
                    daysOfWeek.value = noteList
                    checkedStateFromList.value = !(it)*/
                    //chosenDays.add(index, days[index])
                    checkedStateFromList.value = it
                }
                else if(!checkedStateFromList.value){
                    /*val newList = ArrayList(noteList)
                    newList.removeAt(index)
                    daysOfWeek.value = newList
                    checkedStateFromList.value = it*/
                    //chosenDays.removeAt(index)
                    checkedStateFromList.value = it
                }
                //TODO разобраться с обращением к каждому элементу строки

            }
        )
    }
}

@Composable
fun listOfDaysStates(i: Int): MutableState<Boolean> {
    val san = remember { mutableStateOf(true)}
    val mon = remember { mutableStateOf(true)}
    val tue = remember { mutableStateOf(true)}
    val wend = remember { mutableStateOf(true)}
    val th = remember { mutableStateOf(true)}
    val fr = remember { mutableStateOf(true)}
    val sat = remember { mutableStateOf(true)}
    val listOfWeek = listOf(san, mon, tue, wend, th, fr,sat)

    return listOfWeek[i]
}