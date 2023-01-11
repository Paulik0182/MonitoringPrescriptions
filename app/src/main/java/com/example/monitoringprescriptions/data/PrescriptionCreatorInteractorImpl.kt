package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.TypeMedicine
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
    private val appointmentsRepo: AppointmentsRepo
) : PrescriptionCreatorInteractor {
    override fun create(
        nameMedicine: String,
        prescribedMedicine: String,
        typeMedicine: TypeMedicine,
        dosage: Float,
        unitMeasurement: String,
        comment: String,
        dateStart: Calendar,
        numberDaysTakingMedicine: Int,
        numberAdmissionsPerDay: String,
        medicationsCourse: Float
    ): PrescriptionEntity {
        val prescriptionEntity = PrescriptionEntity(
            id = UUID.randomUUID().toString(),
            nameMedicine = nameMedicine,
            prescribedMedicine = prescribedMedicine,
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
        }
        return prescriptionEntity
    }
}