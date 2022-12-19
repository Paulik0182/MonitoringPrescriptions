package com.example.monitoringprescriptions.data2

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentsEntity
import com.example.monitoringprescriptions.domain.v2.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.v2.repos.AppointmentsRepo
import java.util.*

class AppointmentsRepoImpl(
    private val appointmentsEntity: AppointmentsEntity,
    private val prescriptionEntity: PrescriptionEntity
) : AppointmentsRepo {

    val dateAppointments: MutableList<AppointmentsEntity> = mutableListOf()

    override fun addAppointments(appointmentsEntity: AppointmentsEntity) {
        dateAppointments.add(appointmentsEntity)
    }

    override fun getListAppointments(): List<AppointmentsEntity> {
        return ArrayList(dateAppointments)
    }

    override fun getPrescriptionId(prescriptionId: String): List<AppointmentsEntity?> {
        val prescription = dateAppointments.find {
            it.id == prescriptionId
        }
        return listOf(prescription)

    }

    override fun getByDate(year: Int, month: Int, day: Int): List<AppointmentsEntity> {
        TODO("Not yet implemented")
    }

    init {
        dateAppointments.add(
            AppointmentsEntity(
                id = UUID.randomUUID().toString(),
                time = Calendar.getInstance().timeInMillis,
                status = AppointmentStatus.UNKNOWN,
                prescriptionId = prescriptionEntity.id
            )
        )

        dateAppointments.add(
            AppointmentsEntity(
                id = UUID.randomUUID().toString(),
                time = Calendar.getInstance().timeInMillis + 86_400_000,
                status = AppointmentStatus.UNKNOWN,
                prescriptionId = prescriptionEntity.id
            )
        )
    }
}