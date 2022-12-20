package com.example.monitoringprescriptions.domain.v2.repos

import com.example.monitoringprescriptions.domain.v2.entities.AppointmentEntity

interface AppointmentsRepo {

    fun addAppointments(appointmentsEntity: AppointmentEntity)
    fun getListAppointments(): List<AppointmentEntity>
    fun getPrescriptionAppointments(prescriptionId: String): List<AppointmentEntity?>
    fun getByDate(year: Int, month: Int, day: Int): List<AppointmentEntity>
    fun getById(appointmentId: String): AppointmentEntity?
    fun updateAppointments(copy: AppointmentEntity)
}