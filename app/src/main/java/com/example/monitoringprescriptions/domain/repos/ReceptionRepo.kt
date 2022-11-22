package com.example.monitoringprescriptions.domain.repos

import com.example.monitoringprescriptions.domain.entities.ReceptionEntity

interface ReceptionRepo {

    fun addRecord(receptionEntity: ReceptionEntity)
    fun getRecordList(): List<ReceptionEntity>
    fun removeRecord(recordEntity: ReceptionEntity)
    fun removeAllRecord(onSuccess: (List<ReceptionEntity>) -> Unit)
    fun updateRecord(changedRecord: ReceptionEntity)
}