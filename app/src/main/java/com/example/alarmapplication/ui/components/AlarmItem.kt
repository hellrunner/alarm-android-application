package com.example.alarmapplication.ui.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmapplication.model.Alarm

@Composable
fun AlarmItem(alarm: Alarm, onRemove: () -> Unit, onEdit: () -> Unit) {
    val checkedState = remember { mutableStateOf(alarm.stateOnOff) }

    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onEdit() }, // Добавляем возможность редактирования по клику
        shape = RoundedCornerShape(50.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Отображение времени
            Text(
                text = alarm.time,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Отображение дней недели
            Text(
                text = alarm.days,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(12.dp)
            )

            // Переключатель для включения/выключения будильника
            Switch(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    alarm.stateOnOff = it
                }
            )

            // Кнопка удаления будильника
            IconButton(onClick = onRemove) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Удалить будильник")
            }
        }
    }
}


fun convertDaysToText(days: String): String {
    val dayIndices = days.split(",").mapNotNull { it.trim().toIntOrNull() }
    val dayNames = listOf("Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб")
    return dayIndices.sorted().joinToString(", ") { dayNames.getOrNull(it % 7) ?: "" }
}
