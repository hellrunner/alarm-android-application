package com.example.alarmapplication.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmapplication.MainActivity
import com.example.alarmapplication.R

class SleepReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmTime = intent.getStringExtra("alarmTime") ?: "Неизвестно"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "sleep_reminder_channel_id"
        val channelName = "Sleep Reminder Notifications"

        // Создание канала уведомления для API 26+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Создание и отправка уведомления
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Напоминание о сне")
            .setContentText("Пора ложиться спать! Установленное время: $alarmTime")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(0, notification)
    }
}

