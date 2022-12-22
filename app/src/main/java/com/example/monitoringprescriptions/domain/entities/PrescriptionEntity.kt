package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Описываем рецепт (лекарства) и его прием.
 * Не отдельный прием.
 */

@Parcelize
@Entity(tableName = "prescriptions")
data class PrescriptionEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "name_medicine")
    @SerializedName("name_medicine")
    val nameMedicine: String = "no name",

    @ColumnInfo(name = "prescribed_medicine")
    @SerializedName("prescribed_medicine")
    val prescribedMedicine: String = "Таблетка",

    @ColumnInfo(name = "type_medicine")
    @SerializedName("type_medicine")
    val typeMedicine: TypeMedicine = TypeMedicine.PILL,

    @ColumnInfo(name = "date_start")
    @SerializedName("date_start")
    val dateStart: Long = Calendar.getInstance().timeInMillis, // todo пока не используется

    @ColumnInfo(name = "date_end")
    @SerializedName("date_end")
    val dateEnd: Long = Calendar.getInstance().timeInMillis, // todo пока не используется

    @ColumnInfo(name = "reception_frequency")
    @SerializedName("reception_frequency")
    val receptionFrequency: Int = 1, // todo как описать. пока не используется

    @ColumnInfo(name = "dosage")
    @SerializedName("dosage")
    val dosage: Float = 1.5f,

    @ColumnInfo(name = "unit_measurement")
    @SerializedName("unit_measurement")
    val unitMeasurement: String = "шт.",

    @ColumnInfo(name = "photo")
    @SerializedName("photo")
    val photo: String = "path/to/photo", // todo пока не используется

    @ColumnInfo(name = "comment")
    @SerializedName("comment")
    val comment: String = "нет комментария"

) : Parcelable