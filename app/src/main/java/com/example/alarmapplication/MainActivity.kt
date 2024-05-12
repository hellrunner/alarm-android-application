package com.example.alarmapplication

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.alarmapplication.viewmodel.AlarmsViewModel
import com.example.alarmapplication.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var alarmsViewModel: AlarmsViewModel

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем ViewModel и SharedPreferences
        alarmsViewModel = ViewModelProvider(this)[AlarmsViewModel::class.java]
        sharedPreferences = getSharedPreferences("MainPref", MODE_PRIVATE)

        // Инициализируем будильники из SharedPreferences
        alarmsViewModel.initAlarmsArray(sharedPreferences)

        // Отображаем навигацию
        setContent {
            AppNavigation(alarmsViewModel)
        }
    }

    override fun onStop() {
        super.onStop()

        // Сохраняем все будильники перед выходом
        val alarms = alarmsViewModel.alarms.value
        if (alarms.isNotEmpty()) {
            val editor = sharedPreferences.edit()
            val keysBuilder = StringBuilder()

            for (alarm in alarms) {
                val key = "key_${alarm.index}"
                val alarmData = "${alarm.time} ${
                    alarm.days.replace(
                        " ",
                        ","
                    )
                } ${alarm.stateOnOff} ${alarm.existAlarm} ${alarm.index}"

                editor.putString(key, alarmData)
                keysBuilder.append("$key ")
            }

            // Сохраняем строку с индексами всех будильников
            editor.putString("mainKey", keysBuilder.toString()).apply()
        }
    }
}





