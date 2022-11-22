package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ReceptionEntity(

    @SerializedName("id")
    var id: String = UUID.randomUUID().toString(),

    @SerializedName("name_medicine")
    var nameMedicine: String = "no name",

    @SerializedName("date_start")
    var dateStart: Long = Calendar.getInstance().timeInMillis,

    @SerializedName("date_end")
    var dateEnd: Long = Calendar.getInstance().timeInMillis,

    @SerializedName("time")
    var time: MutableList<Long> = mutableListOf(),

    @SerializedName("reception_frequency")
    var receptionFrequency: Int = 1,

    @SerializedName("result_reception")
    var resultReception: Boolean = false,

    @SerializedName("dosage")
    var dosage: Float = 1.5f,

    @SerializedName("unit_measurement")
    var unitMeasurement: String = "шт.",

    @SerializedName("foto")
    var foto: Int = 0,

    @SerializedName("comment")
    var comment: String = "нет комментария"

) : Parcelable