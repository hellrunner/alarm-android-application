package com.example.alarmapplication

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.alarmapplication.alarm_View_Models.AlarmsViewModel
import com.example.alarmapplication.model.Alarm
import com.example.alarmapplication.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var alarmArray: ArrayList<Alarm>
    private lateinit var alarmsViewModel: AlarmsViewModel

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmArray = arrayListOf()
        alarmsViewModel = ViewModelProvider(this)[AlarmsViewModel::class.java]
        sharedPreferences = applicationContext.getSharedPreferences("MainPref", MODE_PRIVATE)
        alarmsViewModel.initAlarmsArray(sharedPreferences)

        setContent {
            AppNavigation(alarmsViewModel)
        }

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        if (alarmsViewModel.getExistAlarms().isNotEmpty()) {
            //Получаем существующие будильники
            val editor = sharedPreferences.edit()
            var indexOfAlarmsArray = 0
            var strOfKeys = "" //Строка ключей элементов списка будильников
            var watchInEditor = ""

            alarmArray = alarmsViewModel.getExistAlarms()

            while (indexOfAlarmsArray < alarmsViewModel.getExistAlarms().size) { //Идём по всему списку
                val keyOfAlarm = "key_$indexOfAlarmsArray" //Создаём ключ с номером индекса будильника
                watchInEditor = alarmArray[indexOfAlarmsArray].time + " " +
                        alarmArray[indexOfAlarmsArray].days.replace(" ", ",") + " " +
                        alarmArray[indexOfAlarmsArray].stateOnOff.toString() + " " +
                        alarmArray[indexOfAlarmsArray].existAlarm.toString() + " " +
                        alarmArray[indexOfAlarmsArray].index.toString() + " "
                editor.putString(keyOfAlarm, watchInEditor) //Сохраняем данные по индкексу будильника
                editor.apply() //Заканчиваем
                strOfKeys += "$keyOfAlarm " //Сохраняем ключ в строку
                indexOfAlarmsArray++ //Переходим к след. элементу
            }
            editor.putString("mainKey", strOfKeys).apply() //Сохраняем итоговую строку с индексами
        }
        super.onStop()
    }
    //TODO Доделать вызов будильника

}




