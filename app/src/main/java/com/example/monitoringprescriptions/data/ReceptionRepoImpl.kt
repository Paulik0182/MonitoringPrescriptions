package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.domain.entities.RecordEntity
import com.example.monitoringprescriptions.domain.repos.ReceptionRepo
import java.util.*

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


    init {
        dataReception.add(
            ReceptionEntity(
                id = UUID.randomUUID().toString(),
                nameMedicine = "Тизанидин",
                prescribedMedicine = "Таблетка",
                dateStart = Calendar.getInstance().timeInMillis,
                dateEnd = Calendar.getInstance().timeInMillis + 1_000_000,
                time = Calendar.getInstance().timeInMillis + 1_000,
                receptionFrequency = 2,
                dosage = 1.0f,
                unitMeasurement = "шт.",
                resultReception = AppointmentStatus.UNKNOWN,
                typeMedicine = TypeMedicine.PILL,
                comment = "Прием после еды",
                records = listOf(
                    RecordEntity(
                        time = Calendar.getInstance().timeInMillis,
                        status = AppointmentStatus.YES
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
                time = Calendar.getInstance().timeInMillis - 1_000,
                receptionFrequency = 1,
                dosage = 1f,
                unitMeasurement = "шт.",
                resultReception = AppointmentStatus.UNKNOWN,
                typeMedicine = TypeMedicine.PILL,
                comment = "Прием до еды",
                records = listOf(
                    RecordEntity(
                        time = Calendar.getInstance().timeInMillis,
                        status = AppointmentStatus.YES
                    )
                )
            )
        )
    }
}