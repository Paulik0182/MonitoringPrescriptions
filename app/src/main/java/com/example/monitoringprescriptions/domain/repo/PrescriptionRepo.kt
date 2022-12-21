package com.example.monitoringprescriptions.domain.repo

import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity

interface PrescriptionRepo {

    fun addPrescription(prescriptionEntity: PrescriptionEntity)
    fun getListPrescription(): List<PrescriptionEntity>
    fun getId(id: String): PrescriptionEntity?
}