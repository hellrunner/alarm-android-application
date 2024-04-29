package com.example.alarmapplication.alarm_View_Models

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.alarmapplication.model.Alarm

class AlarmsViewModel : ViewModel() {
    private val alarms: ArrayList<Alarm> = arrayListOf()
    var flagOnAlarmScreen: Boolean = true
    var indexOfAlarm = 0
    private lateinit var sharedPreferences: SharedPreferences

    fun initAlarmsArray(sharedP: SharedPreferences){
        sharedPreferences = sharedP
        val mainKey: String? = sharedP.getString("mainKey", "Not Save") //Получаем главный ключ
        if (mainKey != "Not Save"){ //Если он есть
            val arrayOfKeys = sharedPreferences
                .getString("mainKey", "No value")
                ?.split(" ")
                ?.filter { it.isNotEmpty() } //Получаем массив ключей элементов будильников и суём его в массив arrayOfKeys

            if (arrayOfKeys != null){ //Если он не пустой
                for (i in arrayOfKeys.indices){
                    val tempStr =
                        sharedPreferences
                            .getString(arrayOfKeys[i], "No value")
                            ?.split(" ")
                            ?.filter { it.isNotEmpty() } //Получаем данные о будильнике и разделяем их
                    if (tempStr?.get(0).toString() != "No")  {
                        val alarm = Alarm( //Создаём будильник с полученными данными
                            tempStr?.get(0).toString(),
                            tempStr?.get(1).toString(),
                            tempStr?.get(2)?.toBoolean() ?: true,
                            tempStr?.get(3)?.toBoolean() ?: true,
                            tempStr?.get(4)?.toInt() ?: 0
                        )
                        alarms.add(alarm) //Добавляем в массив будильников
                    }
                }
            }
        }
    }

    fun setAlarms(alarm: Alarm) {
        alarms.add(alarm)
    }

    fun getExistAlarms(): ArrayList<Alarm> {
        return alarms
    }

    fun plusToIndexOfAlarm() {
        indexOfAlarm++
    }

}