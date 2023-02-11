package com.example.monitoringprescriptions

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

/**
 * Receiver - нужно регистрировать в Манифесте
 * Нужно помнить о пермишон - получение разрешения от пользователя
 */

private const val ALARM_NOTIFICATION_CHANNEL_ID = "123"

class ReminderReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        val appointmentId = intent.getStringExtra("id")

        val notificationId = UUID.fromString(appointmentId).leastSignificantBits.toInt()
        // наверное лучше сделать экстеншен appointment.getId

        val notificationManager = NotificationManagerCompat.from(context)

        //создаем канал (NotificationManager необходимо создавать канал)
        notificationManager.createNotificationChannel(
            NotificationChannel(
                ALARM_NOTIFICATION_CHANNEL_ID,
                "manager",
                NotificationManager.IMPORTANCE_HIGH
            )
        // можно стартануть активити
        )

        // todo достать appointmentRepo.get(id) и от туда достать имя и др. текст
        val notification =
            NotificationCompat.Builder(context, ALARM_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_appointmen)
                .setContentTitle("Пора принимать лекарства")
                .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // todo
            return
        }

        notificationManager.notify(notificationId, notification)
    }
}
