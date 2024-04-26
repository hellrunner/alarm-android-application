package com.example.alarmapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.alarmapplication.ui.components.DaysOfWeek
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Диалоговое окно для выбора времени с возможностью выбора дней недели.
 *
 * Этот компонент представляет собой модальное диалоговое окно, которое позволяет пользователю выбрать время и дни недели.
 * Включает кнопки для подтверждения выбора или отмены действия. Диалог поддерживает внешнюю настройку через параметры
 * и содержимое диалога, которое определяется вызывающей стороной.
 *
 * @param title Заголовок диалогового окна, по умолчанию "Select Time".
 * @param onCancel Функция, вызываемая при отмене диалога.
 * @param onConfirm Функция, вызываемая при подтверждении выбранного времени.
 * @param toggle Дополнительный компонентный контент, который может быть предоставлен и отображен в диалоге.
 * @param content Композитный контент, который определяет основное тело диалога, например, выбор времени.
 */
@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val week: List<String> = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )

                content()

                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    DaysOfWeek(week)
                }

                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { onCancel() }
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = { onConfirm() }
                    ) { Text("OK") }
                }
            }
        }
    }
}




