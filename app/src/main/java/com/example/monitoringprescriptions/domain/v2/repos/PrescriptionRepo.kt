package com.example.monitoringprescriptions.domain.v2.repos

import com.example.monitoringprescriptions.domain.v2.entities.PrescriptionEntity

interface PrescriptionRepo {

    fun addPrescription(prescriptionEntity: PrescriptionEntity)
    fun getListPrescription(): List<PrescriptionEntity>
    fun getId(id: String): PrescriptionEntity?
}