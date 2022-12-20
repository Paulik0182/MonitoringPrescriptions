package com.example.monitoringprescriptions.data2

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentsEntity
import com.example.monitoringprescriptions.domain.v2.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.v2.repos.AppointmentsRepo
import com.example.monitoringprescriptions.utils.dayIsEqual
import java.util.*

class AppointmentsRepoImpl : AppointmentsRepo {

    private val dataAppointments: MutableList<AppointmentsEntity> = mutableListOf()

    override fun addAppointments(appointmentsEntity: AppointmentsEntity) {
        dataAppointments.add(appointmentsEntity)
    }

    override fun getListAppointments(): List<AppointmentsEntity> {
        return ArrayList(dataAppointments)
    }

    override fun getPrescriptionAppointments(prescriptionId: String): List<AppointmentsEntity> {
        // находим несколько Appointments поэтому filter подходит лучше (фильтруем все сущьности подходящие по условию)
        return dataAppointments.filter {
            it.prescriptionId == prescriptionId
        }
    }

    override fun getByDate(year: Int, month: Int, day: Int): List<AppointmentsEntity> {
        return dataAppointments.filter {
            // собираем из года, месяца и дня - Календарь
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            it.time.dayIsEqual(calendar.timeInMillis)
        }
    }

    init {
        dataAppointments.add(
            AppointmentsEntity(
                id = UUID.randomUUID().toString(),
                time = Calendar.getInstance().timeInMillis,
                status = AppointmentStatus.UNKNOWN,
                prescriptionId = "123"
            )
        )

        // лучше всего использовать объесняющую константу (просто прибавлять непонятное значение неследует)
        val dayInMs = 20 * 60 * 60 * 1000L
        dataAppointments.add(
            AppointmentsEntity(
                id = UUID.randomUUID().toString(),
                time = Calendar.getInstance().timeInMillis + dayInMs,
                status = AppointmentStatus.UNKNOWN,
                prescriptionId = "123"
            )
        )
    }
}