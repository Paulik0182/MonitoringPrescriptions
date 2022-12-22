package com.example.monitoringprescriptions.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity

/**
 * version = 1 - Обязательно должна быть версия, она для того чтобы провести меграцию БД из одной БД
 * в обновленную БД (при меграции делается специальный клас меграции)
 *
 */

@Database(
    entities = [AppointmentEntity::class, PrescriptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
    abstract fun prescriptionDao(): PrescriptionDao
}