package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class UserEntity(

    @SerializedName("id")
    var id: String = UUID.randomUUID().toString(),

    @SerializedName("name")
    var name: String = "no name",

    @SerializedName("email")
    var email: String = ""

) : Parcelable