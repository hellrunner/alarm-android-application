package com.example.alarmapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmapplication.model.Alarm
import com.example.alarmapplication.ui.components.AlarmItem

@Preview
@Composable
fun AlarmScreen() {
    val alarms = listOf(
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),
        Alarm(
            "5:50",
            "Вт",
            true
        ),

        )


    LazyColumn(
        verticalArrangement = Arrangement.Center,
    ) {
        items(alarms) { alarm ->
            AlarmItem(alarm = alarm)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        Alignment.BottomCenter
    ) {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 1.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add by")
        }
    }


}