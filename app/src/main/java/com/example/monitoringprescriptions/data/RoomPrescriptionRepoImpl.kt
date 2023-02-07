package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.data.room.PrescriptionDao
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo

class RoomPrescriptionRepoImpl(
    private val prescriptionDao: PrescriptionDao
) : PrescriptionRepo {

    // добавить
    override fun addPrescription(prescriptionEntity: PrescriptionEntity) {
        prescriptionDao.addPrescription(prescriptionEntity)
    }

    // получить
    override fun getListPrescription(): List<PrescriptionEntity> {
        return prescriptionDao.getAll()
    }

    override fun getPrescription(): PrescriptionEntity? {
        return prescriptionDao.getPrescription()
    }

    override fun getListPrescriptionId(id: String): List<PrescriptionEntity> {
        return prescriptionDao.getListById(id)
    }

    // получить конкретную запись
    override fun getById(id: String): PrescriptionEntity? {
        return prescriptionDao.getById(id)
    }

    override fun updatePrescription(prescriptionEntity: PrescriptionEntity) {
        prescriptionDao.updatePrescription(prescriptionEntity)
    }

    override fun delete(prescriptionEntity: PrescriptionEntity) {
        prescriptionDao.delete(prescriptionEntity)
    }
}