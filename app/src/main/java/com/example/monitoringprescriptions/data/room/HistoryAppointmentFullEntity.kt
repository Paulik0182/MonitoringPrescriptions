package com.example.monitoringprescriptions.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.TypeMedicine

@Entity(tableName = "historyAppointmentFull")
class HistoryAppointmentFullEntity(

    @PrimaryKey(autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "appointmentId")
    val appointmentId: String,

    @ColumnInfo(name = "time")
    val time: Long,

    @ColumnInfo(name = "status")
    val status: AppointmentStatus,

    @ColumnInfo(name = "prescriptionId")
    val prescriptionId: String,

    @ColumnInfo(name = "nameMedicine")
    val nameMedicine: String,

    @ColumnInfo(name = "prescribedMedicine")
    val prescribedMedicine: String,

    @ColumnInfo(name = "typeMedicine")
    val typeMedicine: TypeMedicine,

    @ColumnInfo(name = "dosage")
    val dosage: Float,

    @ColumnInfo(name = "unitMeasurement")
    val unitMeasurement: String
)