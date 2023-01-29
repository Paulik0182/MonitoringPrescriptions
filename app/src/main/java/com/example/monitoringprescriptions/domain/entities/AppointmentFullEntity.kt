package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class AppointmentFullEntity(

    val appointmentId: String,

    val time: Long,

    val status: AppointmentStatus,

    val prescriptionId: String,

    val nameMedicine: String,

    val typeMedicine: TypeMedicine,

    val dosage: Float,

    val unitMeasurement: UnitsMeasurement,

    // немного рассшил сущьность
    val comment: String = "нет комментария",

    val dateStart: Long = Calendar.getInstance().timeInMillis,

    val dateEnd: Long = Calendar.getInstance().timeInMillis + 24 * 60 * 60 * 1_000, // todo пока не используется

    // Время приема
    val timeReceptionTwo: Long?,

    val timeReceptionThree: Long?,

    val timeReceptionFour: Long?,

    val timeReceptionFive: Long?

) : Parcelable