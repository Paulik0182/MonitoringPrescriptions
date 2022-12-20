package com.example.monitoringprescriptions.domain.v2.interactors

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentsFullEntity

interface AppointmentsInteractor {

    // получаем сущьность за конкретный день
    fun getByDate(year: Int, month: Int, dey: Int): List<AppointmentsFullEntity>

    // для обновления - принято, пропущено
    fun changeStatus(appointmentId: String, status: AppointmentStatus)

    // подписка изменения статуса - принято, пропущено
    fun subscribe(callback: (AppointmentEntity) -> Unit)
    fun unsubscribe(callback: (AppointmentEntity) -> Unit)
}