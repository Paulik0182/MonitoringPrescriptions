package com.example.monitoringprescriptions.domain.interactors

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import java.util.*

interface AppointmentsInteractor {

    // для обновления - принято, пропущено
    fun changeStatus(appointmentId: String, status: AppointmentStatus)

    // подписка изменения статуса - принято, пропущено
    fun subscribe(callback: () -> Unit)
    fun unsubscribe(callback: () -> Unit)

    fun getByDate(calendar: Calendar, callback: (List<AppointmentFullEntity>) -> Unit)
    fun delete(prescriptionEntity: PrescriptionEntity) // todo изменил (удаление)!!!!

}