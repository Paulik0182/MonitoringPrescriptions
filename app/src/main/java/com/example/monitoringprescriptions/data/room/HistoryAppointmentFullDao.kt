package com.example.monitoringprescriptions.data.room

import androidx.room.*

@Dao
interface HistoryAppointmentFullDao {
    // получаем все записи
    @Query("SELECT * FROM historyAppointmentFull")
    fun getAll(): List<HistoryAppointmentFullEntity>

    // для фильтрации. чтобы список не пополнялся одинаковыми наименованиями
    @Query("SELECT * FROM historyAppointmentFull WHERE nameMedicine LIKE :nameMedicine")
    fun getDataBy(nameMedicine: String): List<HistoryAppointmentFullEntity>

    // Если будет поподатся такаяже сущьностиь то она будет заменять существующую или можно настроить - будет игнорировать
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyAppointmentFullEntity: HistoryAppointmentFullEntity)

    @Update
    fun update(historyAppointmentFullEntity: HistoryAppointmentFullEntity)

    @Delete
    fun delete(historyAppointmentFullEntity: HistoryAppointmentFullEntity)
}