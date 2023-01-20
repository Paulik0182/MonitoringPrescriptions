package com.example.monitoringprescriptions.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.interactors.PrescriptionCreatorInteractor
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.utils.forEach
import java.util.*

private const val DAY_IN_MS = 24 * 60 * 60 * 1000L

class PrescriptionCreatorInteractorImpl(
    private val prescriptionRepo: PrescriptionRepo,
    private val appointmentsRepo: AppointmentsRepo,
    private val context: Context
) : PrescriptionCreatorInteractor {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun create(
        nameMedicine: String,
        typeMedicine: TypeMedicine,
        dosage: Float,
        unitMeasurement: UnitsMeasurement,
        comment: String,
        dateStart: Calendar,
        numberDaysTakingMedicine: Int,
        numberAdmissionsPerDay: Int,
        medicationsCourse: Float
    ): PrescriptionEntity {
        val prescriptionEntity = PrescriptionEntity(
            id = UUID.randomUUID().toString(),
            nameMedicine = nameMedicine,
            typeMedicine = typeMedicine,
            dosage = dosage,
            unitMeasurement = unitMeasurement,
            comment = comment,
            dateStart = dateStart.timeInMillis,
            numberDaysTakingMedicine = numberDaysTakingMedicine,
            numberAdmissionsPerDay = numberAdmissionsPerDay,
            medicationsCourse = medicationsCourse
        )

        prescriptionRepo.addPrescription(prescriptionEntity)

        // от единицы до количества дней будем (добавляем каждую сущьность по расписанию)
        prescriptionEntity.numberDaysTakingMedicine.forEach {
            val appointmentEntity = AppointmentEntity(
                time = prescriptionEntity.dateStart + it * DAY_IN_MS,
                prescriptionId = prescriptionEntity.id
            )
            appointmentsRepo.addAppointment(appointmentEntity)
            setAlarm(appointmentEntity)
        }
        return prescriptionEntity
    }

    // Добавляем уведомление
    // у каждого appointment есть время, добавляем его, берем всю сущьность, там есть время
    @RequiresApi(Build.VERSION_CODES.S)
    private fun setAlarm(appointmentEntity: AppointmentEntity) {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            // для назначения id
            UUID.fromString(appointmentEntity.id).node().toInt(), // id интента
            intent,
            // флаг, поведение  pendingIntent когда их несколько
            PendingIntent.FLAG_MUTABLE // изменяемый
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