package com.example.monitoringprescriptions.domain.repos

import com.example.monitoringprescriptions.domain.entities.AidNoteEntity

interface AidNoteRepo {

    fun addRecord(aidNoteEntity: AidNoteEntity)
    fun getRecordList(): List<AidNoteEntity>
    fun removeRecord(recordEntity: AidNoteEntity)
    fun removeAllRecord(onSuccess: (List<AidNoteEntity>) -> Unit)
    fun updateRecord(changedRecord: AidNoteEntity)
}