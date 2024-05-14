package com.example.alarmapplication.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmapplication.R
import com.example.alarmapplication.model.Alarm

@Composable
fun AlarmItem(alarm: Alarm, onRemove: () -> Unit, onEdit: () -> Unit, onToggle: (Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(alarm.stateOnOff) }
    val context = LocalContext.current
    val daysText = remember(alarm.days) { convertDaysToText(context, alarm.days) }

    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = onEdit),
        shape = RoundedCornerShape(50.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = alarm.time,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Отображение дней недели
            Text(
                text = alarm.days,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            Switch(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    alarm.stateOnOff = it
                    onToggle(it)
                }
            )

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete_alarm)
                )
            }
        }
    }
}

fun convertDaysToText(context: Context, days: String): String {
    val dayIndices = days.split(",").mapNotNull { it.trim().toIntOrNull() }
    val dayNames = context.resources.getStringArray(R.array.day_names)
    return dayIndices.sorted().joinToString(", ") { dayNames[it % 7] }
}