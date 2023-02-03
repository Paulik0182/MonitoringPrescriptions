package com.example.monitoringprescriptions.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import com.example.monitoringprescriptions.ReminderReceiver
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.interactors.ReceiverReminderInteraction
import com.example.monitoringprescriptions.utils.getIdAsInt

/**
 * Напомининие о приеме лекарств
 */

class ReceiverReminderInteractionImpl(
    private val context: Context
) : ReceiverReminderInteraction {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun setReminder(appointmentEntity: AppointmentEntity) {
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(
            "id",
            appointmentEntity.id
        ) // здесь нужно положить какойто апоинтмен (не целый)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            // для назначения id
            appointmentEntity.getIdAsInt(), // id интента
            intent,
            // флаг, поведение  pendingIntent когда их несколько (xor это объединение; or - или)
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_CANCEL_CURRENT// изменяемый
        )
        // требуестя алярм менеджер (получаем системный сервис)
        // эта сущьность через которую все делаем
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // чтобы не сравнивать версии android (особенность разных версий) можно использовать следующее
        // setAlarmClock() - устанавливает будильник, требуется интент будильника
        // setExact() - примерно поставить alarm
        // setAndAllowWhileIdle() - поставить с разрешением будить
        // setExactAndAllowWhileIdle() - поставить точное задонное время когда будить
        // RTC_WAKEUP - тип который может разбудить
        AlarmManagerCompat.setAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            appointmentEntity.time,
            // отложенный интент который можно запустить потом (PendingIntent - поручаем комуто другому запустить)
            pendingIntent
        )
    }
}