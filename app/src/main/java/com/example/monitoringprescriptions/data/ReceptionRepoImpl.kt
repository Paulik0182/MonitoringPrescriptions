package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.domain.Emoji
import com.example.monitoringprescriptions.domain.EmojiIconMedicine
import com.example.monitoringprescriptions.domain.repos.ReceptionRepo
import java.util.*
import kotlin.collections.ArrayList

class ReceptionRepoImpl: ReceptionRepo {

    var dataReception: MutableList<ReceptionEntity> = mutableListOf()

    override fun addReception(receptionEntity: ReceptionEntity) {
        dataReception.add(receptionEntity)
    }

    override fun getReceptionList(): List<ReceptionEntity> {
        return  ArrayList(dataReception)
    }

    override fun removeReception(recordEntity: ReceptionEntity) {
        dataReception.remove(recordEntity)
    }

    override fun removeAllReception(onSuccess: (List<ReceptionEntity>) -> Unit) {
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
                typeMedicine = "Таблетка",
                time = "09:00",
                dateStart = Calendar.getInstance().timeInMillis,
                dateEnd = Calendar.getInstance().timeInMillis + 1_000_000,
                receptionFrequency = 2,
                resultReception = Emoji.YES,
                dosage = 1.0f,
                unitMeasurement = "шт.",
                iconMedicine = EmojiIconMedicine.PILL,
                comment = "Прием после еды"
            )
        )

        dataReception.add(
            ReceptionEntity(
                id = UUID.randomUUID().toString(),
                nameMedicine = "Йодомарин",
                typeMedicine = "Таблетка",
                time = "12:00",
                dateStart = Calendar.getInstance().timeInMillis + 2_000,
                dateEnd = Calendar.getInstance().timeInMillis + 2_000_000,
                receptionFrequency = 1,
                resultReception = Emoji.UNKNOWN,
                dosage = 1f,
                unitMeasurement = "шт.",
                iconMedicine = EmojiIconMedicine.PILL,
                comment = "Прием до еды"
            )
        )
    }
}