package com.example.monitoringprescriptions.data2

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentsEntity
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentsFullEntity
import com.example.monitoringprescriptions.domain.v2.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.v2.repos.AppointmentsRepo
import com.example.monitoringprescriptions.domain.v2.repos.PrescriptionRepo

class AppointmentsInteractorImpl(
    private val appointmentsRepo: AppointmentsRepo,
    private val prescriptionRepo: PrescriptionRepo
) : AppointmentsInteractor {

    private val listeners: MutableSet<(AppointmentsEntity) -> Unit> = HashSet()

    override fun getByDate(year: Int, month: Int, dey: Int): List<AppointmentsFullEntity> {
        TODO("Not yet implemented")
    }

    override fun changeStatus(appointmentId: String, status: AppointmentStatus) {
//        appointmentsRepo.getPrescriptionId(appointmentId) {
//
//        }
    }

    override fun subscribe(callback: (AppointmentsEntity) -> Unit) {
        listeners.add(callback)
    }

    override fun unsubscribe(callback: (AppointmentsEntity) -> Unit) {
        listeners.remove(callback)
    }
}