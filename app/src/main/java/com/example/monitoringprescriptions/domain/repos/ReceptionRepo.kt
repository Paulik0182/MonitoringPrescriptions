package com.example.monitoringprescriptions.domain.repos

import com.example.monitoringprescriptions.domain.entities.ReceptionEntity

interface ReceptionRepo {

    fun addReception(receptionEntity: ReceptionEntity)
    fun getReceptionList(): List<ReceptionEntity>
    fun removeReception(recordEntity: ReceptionEntity)
    fun clearAll(onSuccess: (List<ReceptionEntity>) -> Unit)
    fun updateReception(changedRecord: ReceptionEntity)
}