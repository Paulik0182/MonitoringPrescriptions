package com.example.monitoringprescriptions.domain.interactors

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.domain.entities.RecordEntity

interface RecordAppointmentInteractor {

    // для пеализации результата - принял, пропустил
    fun makeAppointment(
        receptionEntity: ReceptionEntity,
        recordId: String,
        appointmentStatus: AppointmentStatus
    )

    // подписываемся на изменения (приходит изменившаяся запись)
    fun subscribe(callback: (RecordEntity) -> Unit)

    fun unsubscribe(callback: (RecordEntity) -> Unit)
}