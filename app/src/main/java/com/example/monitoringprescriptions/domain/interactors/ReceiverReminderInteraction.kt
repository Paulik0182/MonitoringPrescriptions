package com.example.monitoringprescriptions.domain.interactors

import com.example.monitoringprescriptions.domain.entities.AppointmentEntity

interface ReceiverReminderInteraction {
    fun setReminder(appointmentEntity: AppointmentEntity)
}