package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class AidNoteEntity(

    @SerializedName("id")
    var id: String = UUID.randomUUID().toString(),

    @SerializedName("heading")
    var heading: String = "Запись к врачу",

    @SerializedName("heading")
    var comment: String = "Теропевт",

    @SerializedName("remind")
    var remind: Boolean = true,

    @SerializedName("date_time_remind")
    var dateTimeRemind: Long = Calendar.getInstance().timeInMillis + 1_000_000_000,

    @SerializedName("date_time_remind_advance")
    var dateTimeRemindAdvance: Long = Calendar.getInstance().timeInMillis + 900_000_000

) : Parcelable