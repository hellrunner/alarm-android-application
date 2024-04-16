package com.example.alarmapplication.ui.screens

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmapplication.R


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


// TODO: Сверстать калькулятор времени (timePicker мб). Реализовать функционал подсчета