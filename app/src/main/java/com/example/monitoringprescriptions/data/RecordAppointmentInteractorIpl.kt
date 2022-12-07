package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.domain.interactors.RecordAppointmentInteractor
import com.example.monitoringprescriptions.domain.repos.ReceptionRepo

class RecordAppointmentInteractorIpl(
    private val receptionRepo: ReceptionRepo
) : RecordAppointmentInteractor {

    private val listeners: MutableSet<() -> Unit> = HashSet()

    override fun makeAppointment(
        receptionEntity: ReceptionEntity,
        recordId: String,
        appointmentStatus: AppointmentStatus
    ) {
        receptionRepo.changeRecord(receptionEntity, recordId, appointmentStatus)
        notifyListeners()
    }

    private fun notifyListeners() {
        listeners.forEach {
            it.invoke()
        }
    }

    override fun subscribe(callback: () -> Unit) {
        listeners.add(callback)
    }

    override fun unsubscribe(callback: () -> Unit) {
        listeners.remove(callback)
    }
}