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

/**
 * Компонент для отображения списка дней недели с чекбоксами.
 *
 * Создает список дней недели, позволяя пользователю выбирать конкретные дни.
 * Использует [Day] для отображения каждого дня недели.
 *
 * @param days Список названий дней недели для отображения.
 */
@Composable
fun DaysOfWeek(
    days: List<String>
) {
    val listMain: ArrayList<String> = arrayListOf(" ", " ", " ", " ", " ", " ", " ")

    for (i in 0..6){
        Day(i, days, listOfDaysStates(i), listMain)
    }
}


/**
 * Компонент для отображения отдельного дня недели с чекбоксом.
 *
 * Отображает название дня и чекбокс, позволяя пользователю включать или отключать
 * выбор дня. При изменении состояния чекбокса, обновляет список выбранных дней.
 *
 * @param index Индекс текущего дня в списке.
 * @param days Список дней недели.
 * @param checkedStateFromList Состояние чекбокса (выбрано/не выбрано).
 * @param chosenDays Список выбранных дней.
 * @param daysOfWeekViewModel Модель представления для управления данными о выбранных днях.
 */
@Composable
fun Day(
    index: Int,
    days: List<String>,
    checkedStateFromList: MutableState<Boolean> = remember { mutableStateOf(true)},
    chosenDays: ArrayList<String>,
    daysOfWeekViewModel: DaysOfWeekViewModel = viewModel()
) {
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
                    daysOfWeekViewModel.setDays(chosenDays)
                }
                else if(checkedStateFromList.value){
                    chosenDays.removeAt(index)
                    chosenDays.add(index, " ")
                    checkedStateFromList.value = it
                    daysOfWeekViewModel.setDays(chosenDays)
                }
            }
        )
    }
}


/**
 * Функция для создания списка состояний чекбоксов для каждого дня недели.
 *
 * Возвращает состояние чекбокса для конкретного дня, инициализированное как [false].
 *
 * @param i Индекс дня недели, для которого создается состояние.
 * @return Состояние чекбокса для указанного дня.
 */
@Composable
fun listOfDaysStates(i: Int): MutableState<Boolean> {
    val san = remember { mutableStateOf(false)}
    val mon = remember { mutableStateOf(false)}
    val tue = remember { mutableStateOf(false)}
    val wed = remember { mutableStateOf(false)}
    val thu = remember { mutableStateOf(false)}
    val fri = remember { mutableStateOf(false)}
    val sat = remember { mutableStateOf(false)}
    val listOfWeek = listOf(san, mon, tue, wed, thu, fri, sat)

    return listOfWeek[i]
}
