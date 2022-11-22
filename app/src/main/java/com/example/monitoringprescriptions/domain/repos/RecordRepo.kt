package com.example.monitoringprescriptions.domain.repos

import com.example.monitoringprescriptions.domain.entities.RecordEntity

interface RecordRepo {

    fun addRecord(recordEntity: RecordEntity)
    fun getRecordList(): List<RecordEntity>
    fun removeRecord(recordEntity: RecordEntity)
    fun removeAllRecord(onSuccess: (List<RecordEntity>) -> Unit)
    fun updateRecord(changedRecord: RecordEntity)
}