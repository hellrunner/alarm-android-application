package com.example.alarmapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

/**
 * Компонентный элемент для отображения информации о будильнике в пользовательском интерфейсе.
 *
 * Этот компонент отображает время будильника, дни недели, когда он активен, и переключатель для включения или отключения будильника.
 * Пользователь может изменить состояние будильника с помощью переключателя.
 *
 * @param alarm Экземпляр класса [Alarm], содержащий информацию о времени и днях активации будильника.
 * Элементы интерфейса используют значения из этого объекта для инициализации и отображения.
 *
 * Дизайн включает округленные углы и тень для визуального выделения элемента в списке.
 */
@Composable
fun AlarmItem(alarm: Alarm) {
    val checkedState = remember { mutableStateOf(alarm.state) }
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(50.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = alarm.time,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = alarm.days,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(12.dp)
                )
                Switch(
                    checked = checkedState.value, onCheckedChange = {
                        checkedState.value = it
                    }
                )
            }

        }
    }
}
