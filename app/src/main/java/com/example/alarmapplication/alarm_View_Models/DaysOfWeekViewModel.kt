package com.example.alarmapplication.alarm_View_Models

import androidx.lifecycle.ViewModel

/**
 * ViewModel для управления выбором дней недели.
 * Обрабатывает операции по установке и получению выбранных дней.
 */
class DaysOfWeekViewModel : ViewModel() {
    // Множество для хранения выбранных дней, инициализировано пустым пространством, чтобы избежать проблем с null.
    private var chosenDays: MutableSet<String> = mutableSetOf(" ")

    /**
     * Обновляет набор выбранных дней на основе предоставленного списка.
     * Дубликаты автоматически удаляются благодаря свойствам множества.
     * @param list список строк, представляющих дни недели.
     */
    fun setDays(list: ArrayList<String>) {
        list.toSet()
        chosenDays.addAll(list)
    }

    /**
     * Возвращает список выбранных дней, исключая любые заполнители.
     * @return ArrayList<String> список выбранных дней недели.
     */
    fun getDays(): ArrayList<String> {
        var _tempList: ArrayList<String> = arrayListOf()
        _tempList.addAll(chosenDays)
        _tempList.removeAll { it == " " }
        return _tempList
    }
}
