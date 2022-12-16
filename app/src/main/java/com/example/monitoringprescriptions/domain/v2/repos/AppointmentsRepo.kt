package com.example.monitoringprescriptions.domain.v2.repos

import com.example.monitoringprescriptions.domain.v2.entities.AppointmentsEntity

interface AppointmentsRepo {

    fun addAppointments(appointmentsEntity: AppointmentsEntity)
    fun getListAppointments(): List<AppointmentsEntity>
    fun getPrescriptionId(prescriptionId: String): List<AppointmentsEntity>
    fun getByDate(year: Int, month: Int, day: Int): List<AppointmentsEntity>
}