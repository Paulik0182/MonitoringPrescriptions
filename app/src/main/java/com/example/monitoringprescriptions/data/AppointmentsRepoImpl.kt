package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.utils.dayIsEqual
import java.util.*

class AppointmentsRepoImpl : AppointmentsRepo {

    private val dataAppointments: MutableList<AppointmentEntity> = mutableListOf()

    override fun addAppointment(appointmentsEntity: AppointmentEntity) {
        dataAppointments.add(appointmentsEntity)
    }

    override fun getListAppointments(): List<AppointmentEntity> {
        return ArrayList(dataAppointments)
    }

    override fun getPrescriptionAppointments(prescriptionId: String): List<AppointmentEntity> {
        // находим несколько Appointments поэтому filter подходит лучше (фильтруем все сущьности подходящие по условию)
        return dataAppointments.filter {
            it.prescriptionId == prescriptionId
        }
    }

    override fun getByDate(year: Int, month: Int, day: Int): List<AppointmentEntity> {
        return dataAppointments.filter {
            // собираем из года, месяца и дня - Календарь
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            it.time.dayIsEqual(calendar.timeInMillis)
        }
    }

    override fun getById(appointmentId: String): AppointmentEntity? {
        return dataAppointments.find {
            it.id == appointmentId
        }
    }

    override fun updateAppointments(appointment: AppointmentEntity) {
        dataAppointments.removeIf { appointment.id == it.id }
        dataAppointments.add(appointment)
    }

    override fun deletePrescriptionAppointments(prescriptionId: String) {
        dataAppointments.removeIf {
            it.prescriptionId == prescriptionId
        }
    }

    init {
        dataAppointments.add(
            AppointmentEntity(
                id = UUID.randomUUID().toString(),
                time = Calendar.getInstance().timeInMillis,
                status = AppointmentStatus.UNKNOWN,
                prescriptionId = "123"
            )
        )

        // лучше всего использовать объесняющую константу (просто прибавлять непонятное значение неследует)
        val dayInMs = 24 * 60 * 60 * 1000L
        dataAppointments.add(
            AppointmentEntity(
                id = UUID.randomUUID().toString(),
                time = Calendar.getInstance().timeInMillis + dayInMs,
                status = AppointmentStatus.UNKNOWN,
                prescriptionId = "123"
            )
        )
    }
}