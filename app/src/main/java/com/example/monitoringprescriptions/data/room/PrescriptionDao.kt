package com.example.monitoringprescriptions.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity

@Dao
interface PrescriptionDao {

    // получаем все записи
    @Query("SELECT * FROM prescriptions")
    fun getAll(): List<PrescriptionEntity>

    @Insert
    fun addPrescription(prescriptionEntity: PrescriptionEntity)

    // Запрос ("ВЫБЕРИТЕ * ИЗ рецептов, ГДЕ id == (:id))
    @Query("SELECT * FROM prescriptions WHERE id == (:id)")
    fun getById(id: String): PrescriptionEntity?
}