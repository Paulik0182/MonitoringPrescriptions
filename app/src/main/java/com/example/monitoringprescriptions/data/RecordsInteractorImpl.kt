package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.entities.RecordEntity
import com.example.monitoringprescriptions.domain.repos.ReceptionRepo
import com.example.monitoringprescriptions.domain.repos.RecordsInteractor
import java.util.*

class RecordsInteractorImpl(
    private val receptionRepo: ReceptionRepo
) : RecordsInteractor {

    override fun getRecordsForDay(
        currentCalendar: Calendar,
        callback: (List<RecordEntity>) -> Unit
    ) {
        val records: MutableList<RecordEntity> = mutableListOf()
        receptionRepo.getReceptionList().forEach { reception ->
            reception.records.forEach { record ->
                // необходимо сраснить совпадения дня
                val recordCalendar = Calendar.getInstance()
                recordCalendar.timeInMillis = record.time

                if (dayIsEqual(currentCalendar, recordCalendar)) {
                    records.add(record)
                }
            }
        }
        callback.invoke(records)
    }

    private fun dayIsEqual(firstCalendar: Calendar, secondCalendar: Calendar): Boolean {
        return firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR)
                && firstCalendar.get(Calendar.DAY_OF_YEAR) == secondCalendar.get(Calendar.DAY_OF_YEAR)

    }

}