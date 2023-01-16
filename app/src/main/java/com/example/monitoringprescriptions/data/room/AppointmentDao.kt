package com.example.monitoringprescriptions.data.room

import androidx.room.*
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity

@Dao
interface AppointmentDao {
    // получаем все записи
    @Query("SELECT * FROM appointment")
    fun getAll(): List<AppointmentEntity>

    @Insert
    fun addAppointment(appointmentsEntity: AppointmentEntity)

    @Query("SELECT * FROM appointment WHERE prescription_id  == (:prescriptionId)")
    fun getPrescriptionAppointments(prescriptionId: String): List<AppointmentEntity>

    // Запрос ("ВЫБЕРИТЕ * ИЗ назначения, ГДЕ время > (:из начало) И время < (:конец)")
    @Query("SELECT * FROM appointment WHERE time > (:fromMs) AND time < (:toMs)")
    fun getByDate(fromMs: Long, toMs: Long): List<AppointmentEntity>

    @Query("SELECT * FROM appointment WHERE id == (:appointmentId)")
    fun getById(appointmentId: String): AppointmentEntity?

    @Update
    fun updateAppointments(copy: AppointmentEntity)

    @Delete
    fun remove(appointmentEntity: AppointmentEntity)

    @Query(
        "DELETE FROM appointment WHERE id == (:appointmentId) AND " +
                "prescription_id == (:prescriptionId)"
    )
    fun remove(prescriptionId: String, appointmentId: String)

    @Delete
    fun remove(appointmentEntity: List<AppointmentEntity>)

}