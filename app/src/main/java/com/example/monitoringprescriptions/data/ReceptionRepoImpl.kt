package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.domain.entities.RecordEntity
import com.example.monitoringprescriptions.domain.repos.ReceptionRepo
import java.util.*

// сортировка по времени
private const val minInMs = 1000 * 60
class ReceptionRepoImpl : ReceptionRepo {

    var dataReception: MutableList<ReceptionEntity> = mutableListOf()

    override fun addReception(receptionEntity: ReceptionEntity) {
        dataReception.add(receptionEntity)
    }

    override fun getReceptionList(): List<ReceptionEntity> {
        return ArrayList(dataReception)
    }

    override fun removeReception(recordEntity: ReceptionEntity) {
        dataReception.remove(recordEntity)
    }

    override fun clearAll(onSuccess: (List<ReceptionEntity>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateReception(changedRecord: ReceptionEntity) {
        TODO("Not yet implemented")
    }

    // чтобы поменять одну запись -
    override fun changeRecord(
        receptionEntity: ReceptionEntity,
        recordId: String,
        appointmentStatus: AppointmentStatus
    ) {
        // достаем рецепт
        val reception = dataReception.find {
            it.id == receptionEntity.id
        } ?: return

        // Удаляем из БД взятый рецепт чтобы создать его заново
        dataReception.remove(reception)

        // После чего из рецепта берем запись
        // records - представляет из себя копию, но изменяемую копию
        val records = mutableListOf<RecordEntity>()
        records.addAll(reception.records)

        // нашли record
        val record = records.find {
            it.id == recordId
        } ?: return
        // удаляем record (конкретная запись)
        records.remove(record)
        // возвращаем (добавляем) record обратно, но с другим статусом
        records.add(record.copy(status = appointmentStatus))

        // добавляем весь новый список в новый рецепт
        val newReception = reception.copy(
            records = records
        )

        // полученное отправляем в БД
        dataReception.add(newReception)
    }


    init {
        dataReception.add(
            ReceptionEntity(
                id = UUID.randomUUID().toString(),
                nameMedicine = "Тизанидин",
                prescribedMedicine = "Таблетка",
                dateStart = Calendar.getInstance().timeInMillis,
                dateEnd = Calendar.getInstance().timeInMillis + 1_000_000,
                receptionFrequency = 2,
                dosage = 1.0f,
                unitMeasurement = "шт.",
                typeMedicine = TypeMedicine.PILL,
                comment = "Прием после еды",
                records = listOf(
                    RecordEntity(
                        time = Calendar.getInstance().timeInMillis,
                        status = AppointmentStatus.UNKNOWN
                    )
                )
            )
        )

        dataReception.add(
            ReceptionEntity(
                id = UUID.randomUUID().toString(),
                nameMedicine = "Йодомарин",
                prescribedMedicine = "Таблетка",
                dateStart = Calendar.getInstance().timeInMillis + 2_000,
                dateEnd = Calendar.getInstance().timeInMillis + 2_000_000,
                receptionFrequency = 1,
                dosage = 1f,
                unitMeasurement = "шт.",
                typeMedicine = TypeMedicine.PILL,
                comment = "Прием до еды",
                records = listOf(
                    RecordEntity(
                        time = Calendar.getInstance().timeInMillis,
                        status = AppointmentStatus.UNKNOWN
                    )
                )
            )
        )
    }
}