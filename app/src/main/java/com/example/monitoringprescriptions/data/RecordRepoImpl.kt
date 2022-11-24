package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.domain.entities.RecordEntity
import com.example.monitoringprescriptions.domain.repos.RecordRepo
import java.util.*
import kotlin.collections.ArrayList

class RecordRepoImpl : RecordRepo {

    private var data: MutableList<RecordEntity> = mutableListOf()
    private val receptionRepoImpl: ReceptionRepoImpl = ReceptionRepoImpl()

    override fun addRecord(recordEntity: RecordEntity) {
        data.add(recordEntity)
    }

    override fun getRecordList(): List<RecordEntity> {
        return ArrayList(data)
    }

    override fun removeRecord(recordEntity: RecordEntity) {
        data.remove(recordEntity)
    }

    override fun removeAllRecord(onSuccess: (List<RecordEntity>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateRecord(changedRecord: RecordEntity) {
        TODO("Not yet implemented")
    }

    init {
        data.add(
            RecordEntity(
                id = UUID.randomUUID().toString(),
                reception = mutableListOf<ReceptionEntity>(
//                    receptionRepoImpl.dataReception.add(
//                        ReceptionEntity(
//                            id = UUID.randomUUID().toString(),
//                            nameMedicine = "Тизанидин",
//                            dateStart = Calendar.getInstance().timeInMillis,
//                            dateEnd = Calendar.getInstance().timeInMillis + 1_000_000,
//                            receptionFrequency = 2,
//                            resultReception = true,
//                            dosage = 1f,
//                            unitMeasurement = "шт.",
//                            comment = "Прием после еды"
//                        )
//                    )
                )
            )
        )
    }
}