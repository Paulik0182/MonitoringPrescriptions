package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.TypeMedicine
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentFullEntity(

    val appointmentId: String,

    val time: Long,

    val status: AppointmentStatus,

    val prescriptionId: String,

    val nameMedicine: String,

    val prescribedMedicine: String,

    val typeMedicine: TypeMedicine,

    val dosage: Float,

    val unitMeasurement: String,

    // немного рассшил сущьность
    val comment: String = "нет комментария"

) : Parcelable