package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Описываем рецепт (лекарства) и его прием.
 * Не отдельный прием.
 */

@Parcelize
data class PrescriptionEntity(

    @SerializedName("id")
    val id: String = UUID.randomUUID().toString(),

    @SerializedName("name_medicine")
    val nameMedicine: String = "no name",

    @SerializedName("prescribed_medicine")
    val prescribedMedicine: String = "Таблетка",

    @SerializedName("type_medicine")
    val typeMedicine: TypeMedicine = TypeMedicine.PILL,

    @SerializedName("date_start")
    val dateStart: Long = Calendar.getInstance().timeInMillis, // todo пока не используется

    @SerializedName("date_end")
    val dateEnd: Long = Calendar.getInstance().timeInMillis, // todo пока не используется

    @SerializedName("reception_frequency")
    val receptionFrequency: Int = 1, // todo как описать. пока не используется

    @SerializedName("dosage")
    val dosage: Float = 1.5f,

    @SerializedName("unit_measurement")
    val unitMeasurement: String = "шт.",

    @SerializedName("photo")
    val photo: String = "path/to/photo", // todo пока не используется

    @SerializedName("comment")
    val comment: String = "нет комментария"

) : Parcelable