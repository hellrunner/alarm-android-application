package com.example.alarmapplication.ui.screens

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.provider.Settings
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmapplication.R
import com.example.alarmapplication.ui.SleepReminderBroadcastReceiver
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MusicScreen() {
    val context = LocalContext.current
    val musicTracks = listOf(
        R.raw.music_fire,
        R.raw.music_forest,
        R.raw.music_water,
        R.raw.music_storm
    )

    val mediaPlayers = remember { mutableStateMapOf<Int, MediaPlayer>() }
    val isPlaying = remember { mutableStateMapOf<Int, Boolean>() }

    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(0, 0)) }

    var sleepTimes by remember { mutableStateOf(listOf<LocalTime>()) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayers.values.forEach { mediaPlayer ->
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }
                mediaPlayer.release()
            }
            mediaPlayers.clear()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            text = stringResource(id = R.string.select_melody)
        )

        Spacer(modifier = Modifier.height(20.dp))

        musicTracks.chunked(2).forEach { rowTracks ->
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowTracks.forEach { track ->
                    MusicButton(
                        trackId = track,
                        mediaPlayers = mediaPlayers,
                        isPlaying = isPlaying,
                        context = context
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Card(
            modifier = Modifier.padding(30.dp),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = stringResource(id = R.string.selected_time, selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = { showTimePicker = true },
                ) {
                    Text(text = stringResource(id = R.string.set_wake_up_time))
                }
            }
        }
        if (showTimePicker) {
            val pickerDialog = TimePickerDialog(
                context,
                { _: TimePicker, hour: Int, minute: Int ->
                    val newWakeUpTime = LocalTime.of(hour, minute)
                    selectedTime = newWakeUpTime
                    sleepTimes = calculateSleepTimes(newWakeUpTime)
                    showTimePicker = false
                },
                selectedTime.hour,
                selectedTime.minute,
                true // Использовать 24-часовой формат
            )
            // Обработчик отмены
            pickerDialog.setOnCancelListener {
                showTimePicker = false
            }
            pickerDialog.show()
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(id = R.string.sleep_at),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        SleepTimeCards(sleepTimes = sleepTimes)
    }
}

@Composable
fun MusicButton(
    trackId: Int,
    mediaPlayers: MutableMap<Int, MediaPlayer>,
    isPlaying: MutableMap<Int, Boolean>,
    context: Context
) {
    Button(
        onClick = {
            val currentPlayer = mediaPlayers.getOrPut(trackId) {
                MediaPlayer.create(context, trackId).apply { isLooping = true }
            }
            val currentIsPlaying = isPlaying[trackId] == true
            // Stop all other tracks before starting this one
            mediaPlayers.forEach { (id, player) ->
                if (id != trackId && isPlaying[id] == true) {
                    player.pause()
                    isPlaying[id] = false
                }
            }
            if (currentIsPlaying) {
                currentPlayer.pause()
            } else {
                currentPlayer.start()
            }
            isPlaying[trackId] = !currentIsPlaying
        },
        modifier = Modifier.size(120.dp, 80.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(
                id = when (trackId) {
                    R.raw.music_fire -> R.drawable.music_fire
                    R.raw.music_forest -> R.drawable.music_forest
                    R.raw.music_water -> R.drawable.music_water
                    else -> R.drawable.music_storm
                }
            ),
            contentDescription = stringResource(id = R.string.music_icon)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateSleepTimes(wakeUpTime: LocalTime): List<LocalTime> {
    val cycleDuration = Duration.ofMinutes(90)
    return List(6) { wakeUpTime.minusMinutes((it + 1) * cycleDuration.toMinutes()) }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SleepTimeCards(sleepTimes: List<LocalTime>) {
    val context = LocalContext.current
    val numberOfRows = 2
    val numberOfItemsPerRow = sleepTimes.size / numberOfRows

    Column {
        for (i in 0 until numberOfRows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (j in 0 until numberOfItemsPerRow) {
                    val sleepTime = sleepTimes.getOrNull(i * numberOfItemsPerRow + j) ?: break
                    TimeCard(time = sleepTime)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeCard(time: LocalTime) {
    val context = LocalContext.current
    val notificationText = stringResource(R.string.notification_set_for, time.format(DateTimeFormatter.ofPattern("HH:mm")))

    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                Toast.makeText(context, notificationText, Toast.LENGTH_SHORT).show()
                setSleepReminder(time, context)
            },
    ) {
        Text(
            text = time.format(DateTimeFormatter.ofPattern("HH:mm")),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun setSleepReminder(time: LocalTime, context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return
    }

    val intent = Intent(context, SleepReminderBroadcastReceiver::class.java)

    val requestCode = time.toSecondOfDay()
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode, // Уникальный код запроса для каждого времени сна
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    var triggerAtMillis = LocalDateTime.now()
        .withHour(time.hour)
        .withMinute(time.minute)
        .withSecond(0)
        .withNano(0)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    // Проверка, не настроено ли время срабатывания на прошлое. Если да, то добавляем сутки.
    val currentMillis = System.currentTimeMillis()
    if (triggerAtMillis <= currentMillis) {
        triggerAtMillis += Duration.ofDays(1).toMillis()
    }

    // Установка точного будильника, который сработает даже в энергосберегающем режиме
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerAtMillis,
        pendingIntent
    )
}