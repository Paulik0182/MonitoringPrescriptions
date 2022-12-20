package com.example.monitoringprescriptions.domain.v2.interactors

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentFullEntity
import java.util.*

interface AppointmentsInteractor {

    // для обновления - принято, пропущено
    fun changeStatus(appointmentId: String, status: AppointmentStatus)

    // подписка изменения статуса - принято, пропущено
    fun subscribe(callback: (AppointmentFullEntity) -> Unit)
    fun unsubscribe(callback: (AppointmentFullEntity) -> Unit)

    fun getByDate(calendar: Calendar, callback: (List<AppointmentFullEntity>) -> Unit)
}