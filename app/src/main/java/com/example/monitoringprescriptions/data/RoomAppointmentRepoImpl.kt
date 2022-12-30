package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.data.room.AppointmentDao
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import java.util.*

class RoomAppointmentRepoImpl(
    private val appointmentDao: AppointmentDao
) : AppointmentsRepo {

    override fun addAppointment(appointmentsEntity: AppointmentEntity) {
        appointmentDao.addAppointment(appointmentsEntity)
    }

    override fun getListAppointments(): List<AppointmentEntity> {
        return appointmentDao.getAll()
    }

    override fun getPrescriptionAppointments(prescriptionId: String): List<AppointmentEntity> {
        return appointmentDao.getPrescriptionAppointments(prescriptionId)
    }

    override fun getByDate(year: Int, month: Int, day: Int): List<AppointmentEntity> {
        val calendar = Calendar.getInstance()
        // Сбросили время на ноль. получили fromMs нулевое (для сравнения)
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val fromMs: Long = calendar.timeInMillis

        // перевели время на 23:59:59. получили toMs (для сравнения)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val toMs: Long = calendar.timeInMillis

        return appointmentDao.getByDate(fromMs, toMs)
    }

    override fun getById(appointmentId: String): AppointmentEntity? {
        return appointmentDao.getById(appointmentId)
    }

    override fun updateAppointments(copy: AppointmentEntity) {
        appointmentDao.updateAppointments(copy)
    }

    override fun deletePrescriptionAppointments(prescriptionId: String) {
        appointmentDao.remove(appointmentDao.getPrescriptionAppointments(prescriptionId))
    }
}