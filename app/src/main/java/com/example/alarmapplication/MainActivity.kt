package com.example.alarmapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmapplication.alarm_View_Models.AlarmsViewModel
import com.example.alarmapplication.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }

    override fun onResume() {

        super.onResume()
    }
}

@Composable
fun SaveAlarms(alarmsViewModel: AlarmsViewModel = viewModel()){

}


