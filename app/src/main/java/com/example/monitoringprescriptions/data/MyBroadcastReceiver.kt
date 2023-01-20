package com.example.monitoringprescriptions.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Receiver - нужно регистрировать в Манифесте
 * Нужно помнить о пермишон - получение разрешения от пользователя
 */

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "привет", Toast.LENGTH_SHORT).show()
    }
}
