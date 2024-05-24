package com.example.alarmapplication

import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.compose.ui.Modifier
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmapplication.model.Flag
import kotlin.random.Random

class AlarmActivity : ComponentActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupMediaPlayer()

        setContent {
            AlarmGame()
        }
    }

    private fun setupMediaPlayer() {
        // Используем ваш файл из res/raw
        val alarmUri: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.music_alarm_main)
        mediaPlayer = MediaPlayer.create(this, alarmUri)
        mediaPlayer?.isLooping = true // Если вы хотите, чтобы звук повторялся
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    @Composable
    fun AlarmGame() {
        val game = remember { Random.nextInt(2) }
        when (game) {
            0 -> FlagGame()
            1 -> ArithmeticGame()
        }
    }

    @Composable
    fun FlagGame() {
        val flags = getFlags()
        val currentFlag = remember { flags.random() }
        val options = remember {
            flags.shuffled().take(4).toMutableList().apply {
                if (!contains(currentFlag)) this[0] = currentFlag
            }.shuffled()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.flag_question),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Image(
                painter = painterResource(id = currentFlag.imageResId),
                contentDescription = stringResource(id = R.string.flag_description, currentFlag.countryName),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            options.forEach { flag ->
                Button(
                    onClick = {
                        if (flag.countryName == currentFlag.countryName) {
                            mediaPlayer?.stop()
                            finish()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(text = flag.countryName)
                }
            }
        }
    }

    @Composable
    fun ArithmeticGame() {
        val firstNumber = remember { Random.nextInt(1, 100) }
        val secondNumber = remember { Random.nextInt(1, 100) }
        val correctAnswer = firstNumber + secondNumber
        val options = remember {
            mutableListOf(
                correctAnswer,
                Random.nextInt(1, 200),
                Random.nextInt(1, 200),
                Random.nextInt(1, 200)
            ).shuffled()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.solve_arithmetic),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "$firstNumber + $secondNumber = ?",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            options.forEach { answer ->
                Button(
                    onClick = {
                        if (answer == correctAnswer) {
                            mediaPlayer?.stop()
                            finish()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(text = answer.toString())
                }
            }
        }
    }

    @Composable
    fun getFlags(): List<Flag> {
        return listOf(
            Flag(R.drawable.flag_usa, stringResource(id = R.string.country_usa)),
            Flag(R.drawable.flag_canada, stringResource(id = R.string.country_canada)),
            Flag(R.drawable.flag_germany, stringResource(id = R.string.country_germany)),
            Flag(R.drawable.flag_france, stringResource(id = R.string.country_france)),
            Flag(R.drawable.flag_italy, stringResource(id = R.string.country_italy)),
            Flag(R.drawable.flag_japan, stringResource(id = R.string.country_japan)),
            Flag(R.drawable.flag_brazil, stringResource(id = R.string.country_brazil)),
            Flag(R.drawable.flag_india, stringResource(id = R.string.country_india)),
            Flag(R.drawable.flag_australia, stringResource(id = R.string.country_australia)),
            Flag(R.drawable.flag_russia, stringResource(id = R.string.country_russia))
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AlarmGame()
    }
}