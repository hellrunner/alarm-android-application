package com.example.alarmapplication

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmapplication.model.Flag

class AlarmActivity : ComponentActivity() {
    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRingtone()
        setContent {
            AlarmGame()
        }
    }

    private fun setupRingtone() {
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(applicationContext, alarmUri)
        ringtone?.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        ringtone?.stop()
    }

    @Composable
    fun AlarmGame() {
        val flags = getFlags()
        val currentFlag = remember { flags.random() }
        val options = remember {
            flags.shuffled().take(4).toMutableList().apply {
                if (!contains(currentFlag)) this[0] = currentFlag
            }.shuffled()
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Флаг какой страны?",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            Image(
                painter = painterResource(id = currentFlag.imageResId),
                contentDescription = "Флаг ${currentFlag.countryName}",
                modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            options.forEach { flag ->
                Button(
                    onClick = {
                        if (flag.countryName == currentFlag.countryName) {
                            finish()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                ) {
                    Text(text = flag.countryName)
                }
            }
        }
    }

    fun getFlags(): List<Flag> {
        return listOf(
            Flag(R.drawable.flag_usa, "США"),
            Flag(R.drawable.flag_canada, "Канада"),
            Flag(R.drawable.flag_germany, "Германия"),
            Flag(R.drawable.flag_france, "Франция"),
            Flag(R.drawable.flag_italy, "Италия"),
            Flag(R.drawable.flag_japan, "Япония"),
            Flag(R.drawable.flag_brazil, "Бразилия"),
            Flag(R.drawable.flag_india, "Индия"),
            Flag(R.drawable.flag_australia, "Австралия"),
            Flag(R.drawable.flag_russia, "Россия")
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AlarmActivity().AlarmGame()
    }

}