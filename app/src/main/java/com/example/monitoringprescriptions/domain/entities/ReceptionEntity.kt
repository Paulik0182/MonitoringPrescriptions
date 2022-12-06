package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Описываем рецепт (лекарства) и его прием.
 * Не отдельный прием.
 */

@Parcelize
data class ReceptionEntity(

    @SerializedName("id")
    val id: String = UUID.randomUUID().toString(),

    @SerializedName("name_medicine")
    val nameMedicine: String = "no name",

    @SerializedName("prescribed_medicine")
    val prescribedMedicine: String = "Таблетка",

    @SerializedName("date_start")
    val dateStart: Long = Calendar.getInstance().timeInMillis,

    @SerializedName("date_end")
    val dateEnd: Long = Calendar.getInstance().timeInMillis,

    @SerializedName("time")
    val time: Long,

    @SerializedName("reception_frequency")
    val receptionFrequency: Int = 1, // todo как описать

    @SerializedName("result_reception")
    var resultReception: AppointmentStatus = AppointmentStatus.UNKNOWN,

    @SerializedName("dosage")
    val dosage: Float = 1.5f,

    @SerializedName("unit_measurement")
    val unitMeasurement: String = "шт.",

    @SerializedName("icon_medicine")
    val typeMedicine: TypeMedicine = TypeMedicine.PILL,

    @SerializedName("photo")
    val photo: String = "path/to/photo",

    @SerializedName("comment")
    val comment: String = "нет комментария",

    @SerializedName("records")
    val records: List<RecordEntity> = emptyList()

) : Parcelable