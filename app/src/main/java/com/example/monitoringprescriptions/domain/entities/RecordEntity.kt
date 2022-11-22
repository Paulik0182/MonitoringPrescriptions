package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class RecordEntity(

    @SerializedName("id")
    var id: String = UUID.randomUUID().toString(),

    @SerializedName("reception")
    var reception: MutableList<ReceptionEntity> = mutableListOf(),

    @SerializedName("aid_note")
    var aidNote: MutableList<AidNoteEntity> = mutableListOf()

) : Parcelable