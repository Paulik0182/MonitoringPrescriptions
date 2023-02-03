package com.example.monitoringprescriptions.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.interactors.PrescriptionCreatorInteractor
import com.example.monitoringprescriptions.domain.interactors.ReceiverReminderInteraction
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.utils.forEach
import java.util.*

private const val DAY_IN_MS = 24 * 60 * 60 * 1000L

class PrescriptionCreatorInteractorImpl(
    private val prescriptionRepo: PrescriptionRepo,
    private val appointmentsRepo: AppointmentsRepo,
    private val receiverReminderInteraction: ReceiverReminderInteraction,
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
        medicationsCourse: Float,

        // Время приема
        timeReceptionTwo: Long?,
        timeReceptionThree: Long?,
        timeReceptionFour: Long?,
        timeReceptionFive: Long?
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
            medicationsCourse = medicationsCourse,

            // Время приема
            timeReceptionTwo = timeReceptionTwo,
            timeReceptionThree = timeReceptionThree,
            timeReceptionFour = timeReceptionFour,
            timeReceptionFive = timeReceptionFive
        )

        prescriptionRepo.addPrescription(prescriptionEntity)

        // от единицы до количества дней будем (добавляем каждую сущьность по расписанию)
        prescriptionEntity.numberDaysTakingMedicine.forEach { dayNumber ->
            // дополнительный цык - количество приемов в день
            prescriptionEntity.numberAdmissionsPerDay.forEach {
                val dateStart = when (it) {
                    0 -> prescriptionEntity.dateStart
                    1 -> prescriptionEntity.timeReceptionTwo
                    2 -> prescriptionEntity.timeReceptionThree
                    3 -> prescriptionEntity.timeReceptionFour
                    4 -> prescriptionEntity.timeReceptionFive
                    else -> throw IllegalStateException("Не верное количество приемов в день (более 5)$it")
                } ?: throw IllegalStateException("Ошибка в количестве приемов в день (более 5)$it")
                val appointmentEntity = AppointmentEntity(
                    time = dateStart + dayNumber * DAY_IN_MS,
                    prescriptionId = prescriptionEntity.id
                )
                appointmentsRepo.addAppointment(appointmentEntity)
                receiverReminderInteraction.setReminder(appointmentEntity)
            }
        }
        return prescriptionEntity
    }
}
