package com.example.monitoringprescriptions.domain.interactors

import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair
import java.util.*

interface RecordsInteractor {

    fun getRecordsForDay(
        currentCalendar: Calendar,
        callback: (List<ReceptionRecordPair>) -> Unit
    )
}