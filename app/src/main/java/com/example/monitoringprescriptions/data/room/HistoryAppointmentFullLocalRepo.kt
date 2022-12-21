package com.example.monitoringprescriptions.data.room

import com.example.monitoringprescriptions.domain.AppointmentStatus
import java.util.*

interface HistoryAppointmentFullLocalRepo {

    fun getAllHistory(calendar: Calendar, callback: (List<HistoryAppointmentFullEntity>) -> Unit)
    fun saveEntity(historyAppointmentFullEntity: HistoryAppointmentFullEntity)

    // для обновления - принято, пропущено
    fun changeStatus(appointmentId: String, status: AppointmentStatus)

    // подписка изменения статуса - принято, пропущено
    fun subscribe(callback: () -> Unit)
    fun unsubscribe(callback: () -> Unit)
}