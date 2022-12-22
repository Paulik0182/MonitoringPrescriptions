package com.example.monitoringprescriptions.domain.repo

import com.example.monitoringprescriptions.domain.entities.AppointmentEntity

interface AppointmentsRepo {

    fun addAppointment(appointmentsEntity: AppointmentEntity)
    fun getListAppointments(): List<AppointmentEntity>
    fun getPrescriptionAppointments(prescriptionId: String): List<AppointmentEntity?>
    fun getByDate(year: Int, month: Int, day: Int): List<AppointmentEntity>
    fun getById(appointmentId: String): AppointmentEntity?
    fun updateAppointments(copy: AppointmentEntity)
}