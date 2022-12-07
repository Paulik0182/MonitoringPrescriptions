package com.example.monitoringprescriptions.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceptionRecordPair(
    val receptionEntity: ReceptionEntity,
    val recordEntity: RecordEntity
) : Parcelable