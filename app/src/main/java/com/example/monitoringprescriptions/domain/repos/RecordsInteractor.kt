package com.example.monitoringprescriptions.domain.repos

import com.example.monitoringprescriptions.domain.entities.RecordEntity
import java.util.*

interface RecordsInteractor {

    fun getRecordsForDay(currentCalendar: Calendar, callback: (List<RecordEntity>) -> Unit)
}