package com.example.alarmapplication

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

class AlarmActivity : ComponentActivity() {

    private lateinit var ringtone: Ringtone
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text(text = "Будильник звонит!")
        }

        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, notification)

        ringtone.play()
    }

    override fun onDestroy() {
        if(ringtone.isPlaying){
            ringtone.stop()
        }
        super.onDestroy()
    }
}