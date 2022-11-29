package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class RecordEntity(

    @SerializedName("id")
    var id: String = UUID.randomUUID().toString(),

    @SerializedName("time")
    var time: Long,

    @SerializedName("status")
    val status: AppointmentStatus = AppointmentStatus.UNKNOWN


) : Parcelable