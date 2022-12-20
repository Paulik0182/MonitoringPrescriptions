package com.example.monitoringprescriptions.domain.v2.entities

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

    val unitMeasurement: String

) : Parcelable