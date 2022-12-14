package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "appointment")
data class AppointmentEntity(

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "time")
    @SerializedName("time")
    var time: Long,

    @ColumnInfo(name = "status")
    @SerializedName("status")
    val status: AppointmentStatus = AppointmentStatus.UNKNOWN,

    @ColumnInfo(name = "prescription_id")
    @SerializedName("prescription_id")
    val prescriptionId: String

) : Parcelable