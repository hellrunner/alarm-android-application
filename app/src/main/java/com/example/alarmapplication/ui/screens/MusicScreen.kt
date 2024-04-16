package com.example.alarmapplication.ui.screens

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmapplication.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            text = "Выберете мелодию"
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
            modifier = Modifier.padding(10.dp),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Выбранное время: ${selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { showTimePicker = true },
        ) {
            Text(text = "Установить время пробуждения")
        }
        if (showTimePicker) {
            val pickerDialog = android.app.TimePickerDialog(
                context,
                { _, hour, minute ->
                    selectedTime = LocalTime.of(hour, minute)
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
            mediaPlayers.getOrPut(trackId) { MediaPlayer.create(context, trackId) }.apply {
                if (isPlaying[trackId] == true) {
                    pause()
                    isPlaying[trackId] = false
                } else {
                    start()
                    isPlaying[trackId] = true
                    isLooping = true
                }
            }
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
            contentDescription = "Music Icon"
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ViewMusic() {
    MusicScreen()
}